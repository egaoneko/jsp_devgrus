package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
import net.devgrus.comment.handler.*;
import net.devgrus.comment.handler.ListHandler;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReadHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int articleId = Integer.parseInt(request.getParameter("articleId"));

        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();

            ArticleDao articleDao = ArticleDao.getInstance();
            Article article = articleDao.selectById(conn, articleId);

            if(article == null){
                throw new ArticleNotFoundException("게시글이 존재하지 않음 : "+articleId);
            }

            articleDao.increaseReadCount(conn, articleId);
            article.setReadCount(article.getReadCount() + 1);

            request.setAttribute("article", article);

            // 댓글을 생성하기 위해 comment handler 중 list handler 를 생성하여 사용
            net.devgrus.comment.handler.ListHandler listHandler = new ListHandler();
            listHandler.process(request, response);

            return "/view/board/read.jsp";
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }
}
