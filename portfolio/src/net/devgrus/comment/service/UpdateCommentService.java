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
 * 2015-01-04
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class UpdateCommentService {
    private static UpdateCommentService instance = new UpdateCommentService();
    public static UpdateCommentService getInstance(){
        return instance;
    }

    private UpdateCommentService(){
    }

    public Comment update(UpdateRequest updateRequest) throws  CommentNotFoundException, InvalidPasswordException{
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            CommentCheckHelper checkHelper = new CommentCheckHelper();
            checkHelper.checkExistsAndPassword(conn, updateRequest.getCommentId(), updateRequest.getPassword());
            Comment updateComment = new Comment();
            updateComment.setId(updateRequest.getCommentId());
            updateComment.setContent(updateRequest.getContent());

            CommentDao commentDao = CommentDao.getInstance();
            int updateCount = commentDao.update(conn, updateComment);
            if(updateCount == 0){
                throw new CommentNotFoundException("댓글이 존재하지 않음 : " + updateRequest.getCommentId());
            }
            Comment comment = commentDao.selectById(conn, updateRequest.getCommentId());
            conn.commit();
            return comment;
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

