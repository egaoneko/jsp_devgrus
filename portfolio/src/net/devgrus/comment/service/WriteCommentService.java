package net.devgrus.comment.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-03
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WriteCommentService {

    private static WriteCommentService instance = new WriteCommentService();
    public static WriteCommentService getInstance(){
        return instance;
    }

    private WriteCommentService(){}

    public Comment write(WritingRequest writingRequest) throws IdGenerationFailedException{
        int groupId = IdGenerator.getInstance().generateNextId("comment");
        Comment comment = writingRequest.toComment();

        comment.setGroupId(groupId);
        comment.setPostingDate(new Date());
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
            return comment;
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
