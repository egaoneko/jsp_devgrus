package net.devgrus.comment.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.handler.ArticleNotFoundException;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-24
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ListHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String articleIdString = request.getParameter("articleId");

        if(articleIdString == null || articleIdString.equals("")) {
            throw new ArticleNotFoundException("게시글이 존재하지 않음 : " + articleIdString);
        }

        int articleId = Integer.parseInt(articleIdString);

        if(articleId < 0){
            throw new IllegalArgumentException("article id < 1 : " + articleId);
        }

        CommentDao commentDao = CommentDao.getInstance();
        Connection conn = null;

        try{
            conn = ConnectionProvider.getConnection();
            int totalCommentCount = commentDao.selectCount(conn, articleId);

            if(totalCommentCount == 0){
                request.setAttribute("commentList", Collections.<Comment>emptyList());
                request.setAttribute("hasComment", new Boolean(false));
                return "/view/comment/list.jsp";
            }

            List<Comment> commentList = commentDao.select(conn, articleId);
            request.setAttribute("commentList", commentList);
            request.setAttribute("hasComment", new Boolean(true));

            return "/view/comment/list.jsp";
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }
}
