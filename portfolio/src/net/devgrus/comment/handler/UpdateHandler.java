package net.devgrus.comment.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-25
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class UpdateHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int articleId = Integer.parseInt(request.getParameter("articleId"));
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        String password = request.getParameter("password");
        String content = request.getParameter("content");

        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            CommentCheckHelper checkHelper = new CommentCheckHelper();
            checkHelper.checkExistsAndPassword(conn, commentId, password);
            Comment updateComment = new Comment();
            updateComment.setId(commentId);
            updateComment.setContent(content);

            CommentDao commentDao = CommentDao.getInstance();
            int updateCount = commentDao.update(conn, updateComment);
            if(updateCount == 0){
                throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentId);
            }
            Comment comment = commentDao.selectById(conn, commentId);
            conn.commit();

            request.setAttribute("comment", comment);
            return "/view/comment/update.jsp";
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } catch (CommentNotFoundException e){
            JdbcUtil.rollback(conn);
            throw e;
        } catch (InvalidPasswordException e){
            JdbcUtil.rollback(conn);
            throw e;
        } finally {
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e){
                }
                JdbcUtil.close(conn);
            }
        }
    }
}
