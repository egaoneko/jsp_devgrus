package net.devgrus.comment.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-04
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DeleteCommentService {
    private static DeleteCommentService instance = new DeleteCommentService();
    public static DeleteCommentService getInstance(){
        return instance;
    }

    private DeleteCommentService(){
    }

    public void deleteComment(DeleteRequest deleteRequest) throws CommentNotFoundException, InvalidPasswordException{
        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            CommentCheckHelper checkHelper = new CommentCheckHelper();
            checkHelper.checkExistsAndPassword(conn, deleteRequest.getCommentId(), deleteRequest.getPassword());

            CommentDao commentDao = CommentDao.getInstance();
            commentDao.delete(conn, deleteRequest.getCommentId());
            commentDao.decreaseCommentCount(conn, deleteRequest.getArticleId());

            conn.commit();
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

