package net.devgrus.comment.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;
import net.devgrus.comment.model.CommentListModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-02
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ListCommentService {
    private static ListCommentService instance = new ListCommentService();
    public static ListCommentService getInstance(){
        return instance;
    }

    private ListCommentService() {}

    public static final int COUNT_PER_PAGE = 10;

    public CommentListModel getCommentList(int articleId){
        if(articleId < 0){
            throw new IllegalArgumentException("article id < 1 : " + articleId);
        }

        CommentDao commentDao = CommentDao.getInstance();
        Connection conn = null;

        try{
            conn = ConnectionProvider.getConnection();
            int totalCommentCount = commentDao.selectCount(conn, articleId);

            if(totalCommentCount == 0){
                return new CommentListModel();
            }

            List<Comment> commentList = commentDao.select(conn, articleId);
            CommentListModel commentListModel = new CommentListModel(
                    commentList, articleId);
            return  commentListModel;
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }
}
