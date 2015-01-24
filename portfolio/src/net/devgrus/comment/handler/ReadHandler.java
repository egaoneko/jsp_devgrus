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
 * 2015-01-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReadHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String commentIdString = request.getParameter("commentId");

        if(commentIdString == null || commentIdString.equals("")) {
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentIdString);
        }

        int commentId = Integer.parseInt(commentIdString);

        if(commentId < 0){
            throw new IllegalArgumentException("comment id < 1 : " + commentId);
        }

        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            CommentDao commentDao = CommentDao.getInstance();
            Comment comment = commentDao.selectById(conn, commentId);

            if (comment == null) {
                throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentId);
            }

            request.setAttribute("comment", comment);

            return "/view/comment/read.jsp";
        } catch (SQLException e) {
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }
}
