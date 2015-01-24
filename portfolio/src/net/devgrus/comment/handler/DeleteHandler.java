package net.devgrus.comment.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DeleteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int articleId = Integer.parseInt(request.getParameter("articleId"));
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        String password = request.getParameter("password");

        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            CommentCheckHelper checkHelper = new CommentCheckHelper();
            checkHelper.checkExistsAndPassword(conn, commentId, password);

            CommentDao commentDao = CommentDao.getInstance();
            commentDao.delete(conn, commentId);
            commentDao.decreaseCommentCount(conn, articleId);

            conn.commit();

            return "/view/comment/delete.jsp";
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException(e);
        } catch (CommentNotFoundException e){
            JdbcUtil.rollback(conn);
            throw e;
        } catch (InvalidPasswordException e){
            JdbcUtil.rollback(conn);
            throw e;
        } finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                } catch (SQLException e){
                }
            }
            JdbcUtil.close(conn);
        }
    }
}
