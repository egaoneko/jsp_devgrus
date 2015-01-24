package net.devgrus.comment.handler;

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
public class CommentCheckHelper {

    public Comment checkExistsAndPassword(Connection conn, int commentId, String password) throws SQLException,
            CommentNotFoundException, InvalidPasswordException {
        CommentDao commentDao = CommentDao.getInstance();
        Comment comment = commentDao.selectById(conn, commentId);
        if(comment == null){
            throw new CommentNotFoundException("댓글이 존재하지 않음 : " + comment);
        }
        if (!checkPassword(comment.getPassword(), password)){
            throw new InvalidPasswordException("암호 틀림");
        }
        return comment;
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

