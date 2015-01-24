package net.devgrus.board.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-19
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DeleteArticleService {

    private static DeleteArticleService instance = new DeleteArticleService();
    public static DeleteArticleService getInstance(){
        return instance;
    }

    private DeleteArticleService(){
    }

    public void deleteArticle(DeleteRequest deleteRequest) throws ArticleNotFoundException, InvalidPasswordException{
        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            ArticleCheckHelper checkHelper = new ArticleCheckHelper();
            checkHelper.checkExistsAndPassword(conn, deleteRequest.getArticleId(), deleteRequest.getPassword());

            ArticleDao articleDao = ArticleDao.getInstance();
            articleDao.delete(conn, deleteRequest.getArticleId());

            conn.commit();
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException(e);
        } catch (ArticleNotFoundException e){
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
