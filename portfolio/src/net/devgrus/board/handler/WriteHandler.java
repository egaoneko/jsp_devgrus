package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
import net.devgrus.board.service.*;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WriteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int groupId = net.devgrus.board.service.IdGenerator.getInstance().generateNextId("article");

        Article article = new Article();
        article.setWriterName(request.getParameter("writerName"));
        article.setPassword(request.getParameter("password"));
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
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
            request.setAttribute("postedArticle", article);
            return "/view/board/write.jsp";
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
