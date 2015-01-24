package net.devgrus.board.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-19
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReadArticleService {

    private static ReadArticleService instance = new ReadArticleService();
    public static ReadArticleService getInstance(){
        return instance;
    }

    private ReadArticleService(){}

    public Article readArticle(int articleId) throws ArticleNotFoundException{
        return selectArticle(articleId, true);
    }

    private Article selectArticle(int articleId, boolean increaseCount) throws ArticleNotFoundException{
        Connection conn = null;

        try{
            conn = ConnectionProvider.getConnection();

            ArticleDao articleDao = ArticleDao.getInstance();
            Article article = articleDao.selectById(conn, articleId);

            if(article == null){
                throw new ArticleNotFoundException("게시글이 존재하지 않음 : "+articleId);
            }

            if(increaseCount){
                articleDao.increaseReadCount(conn, articleId);
                article.setReadCount(article.getReadCount() + 1);
            }

            return article;
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    public Article getArticle(int articleId) throws ArticleNotFoundException{
        return selectArticle(articleId, false);
    }
}
