package net.devgrus.comment.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;
import net.devgrus.comment.service.IdGenerator;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WriteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int groupId = IdGenerator.getInstance().generateNextId("comment");
        Comment comment = new Comment();
        comment.setWriterName(request.getParameter("writerName"));
        comment.setPassword(request.getParameter("password"));
        comment.setContent(request.getParameter("content"));
        comment.setGroupId(groupId);
        comment.setPostingDate(new Date());
        comment.setArticleId(Integer.parseInt(request.getParameter("articleId")));
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        comment.setSequenceNumber(decimalFormat.format(groupId)+"999999");

        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int commentId = CommentDao.getInstance().insert(conn, comment);
            if(commentId == -1){
                JdbcUtil.rollback(conn);
                throw new RuntimeException("DB 삽입 실패 : " + commentId);
            }
            CommentDao.getInstance().increaseCommentCount(conn, comment.getArticleId());
            conn.commit();

            comment.setId(commentId);
            request.setAttribute("postedComment", comment);

            return "/view/comment/write.jsp";
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                } catch (SQLException e){
                }
                JdbcUtil.close(conn);
            }
        }
    }
}
