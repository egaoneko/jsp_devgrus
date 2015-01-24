package net.devgrus.comment.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-03
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReadCommentService {

    private static ReadCommentService instance = new ReadCommentService();

    public static ReadCommentService getInstance() {
        return instance;
    }

    private ReadCommentService() {
    }

    public Comment readComment(int commentId) throws CommentNotFoundException {
        Connection conn = null;

        try {
            conn = ConnectionProvider.getConnection();

            CommentDao commentDao = CommentDao.getInstance();
            Comment comment = commentDao.selectById(conn, commentId);

            if (comment == null) {
                throw new CommentNotFoundException("댓글이 존재하지 않음 : " + commentId);
            }

            return comment;
        } catch (SQLException e) {
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }
}

