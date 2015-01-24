package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
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
public class DeleteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String password = request.getParameter("password");

        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            ArticleCheckHelper checkHelper = new ArticleCheckHelper();
            checkHelper.checkExistsAndPassword(conn, articleId, password);

            ArticleDao articleDao = ArticleDao.getInstance();
            articleDao.delete(conn, articleId);

            conn.commit();

            return "/view/board/delete.jsp";
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
