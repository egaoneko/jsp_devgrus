package net.devgrus.board.handler;

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
public class ArticleCheckHelper {

    public Article checkExistsAndPassword(Connection conn, int articleId, String password) throws SQLException,
            ArticleNotFoundException, InvalidPasswordException{
        ArticleDao articleDao = ArticleDao.getInstance();
        Article article = articleDao.selectById(conn, articleId);
        if(article == null){
            throw new ArticleNotFoundException("게시글이 존재하지 않음 : " + articleId);
        }
        if (!checkPassword(article.getPassword(), password)){
            throw new InvalidPasswordException("암호 틀림");
        }
        return article;
    }

    private boolean checkPassword(String realPassword, String userInputPassword){
        if(realPassword == null){
            return false;
        }
        if(realPassword.length() == 0){
            return false;
        }
        return realPassword.equals(userInputPassword);
    }
}
