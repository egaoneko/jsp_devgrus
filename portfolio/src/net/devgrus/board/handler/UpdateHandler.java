package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
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
public class UpdateHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            ArticleCheckHelper checkHelper = new ArticleCheckHelper();
            checkHelper.checkExistsAndPassword(conn, articleId, password);
            Article updateArticle = new Article();
            updateArticle.setId(articleId);
            updateArticle.setTitle(title);
            updateArticle.setContent(content);

            ArticleDao articleDao = ArticleDao.getInstance();
            int updateCount = articleDao.update(conn, updateArticle);
            if(updateCount == 0){
                throw new ArticleNotFoundException("게시글이 존재하지 않음 : " + articleId);
            }
            Article article = articleDao.selectById(conn, articleId);
            conn.commit();

            request.setAttribute("article", article);

            return "/view/board/update.jsp";
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } catch (ArticleNotFoundException e){
            JdbcUtil.rollback(conn);
            throw e;
        } catch (InvalidPasswordException e){
            JdbcUtil.rollback(conn);
            throw e;
        } finally {
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e){
                }
                JdbcUtil.close(conn);
            }
        }
    }
}
