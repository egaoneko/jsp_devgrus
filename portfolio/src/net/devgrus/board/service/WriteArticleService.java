package net.devgrus.board.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-19
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WriteArticleService {

    private static WriteArticleService instance = new WriteArticleService();
    public static WriteArticleService getInstance(){
        return instance;
    }

    private WriteArticleService(){}

    public Article write(WritingRequest writingRequest) throws IdGenerationFailedException{
        int groupId = IdGenerator.getInstance().generateNextId("article");
        Article article = writingRequest.toArticle();

        article.setGroupId(groupId);
        article.setPostingDate(new Date());
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        article.setSequenceNumber(decimalFormat.format(groupId)+"999999");

        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            int articleId = ArticleDao.getInstance().insert(conn, article);
            if(articleId == -1){
                JdbcUtil.rollback(conn);
                throw new RuntimeException("DB 삽입 실패 : " + articleId);
            }

            conn.commit();

            article.setId(articleId);
            return article;
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
