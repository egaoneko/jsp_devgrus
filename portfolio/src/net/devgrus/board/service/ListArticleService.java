package net.devgrus.board.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
import net.devgrus.board.model.ArticleListModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ListArticleService {
    private static ListArticleService instance = new ListArticleService();
    public static ListArticleService getInstance(){
        return instance;
    }

    private ListArticleService() {}

    public static final int COUNT_PER_PAGE = 10;

    public ArticleListModel getArticleList(int requestPageNumber){
        if(requestPageNumber < 0){
            throw new IllegalArgumentException("page number < 0 : " + requestPageNumber);
        }

        ArticleDao articleDao = ArticleDao.getInstance();
        Connection conn = null;

        try{
            conn = ConnectionProvider.getConnection();
            int totalArticleCount = articleDao.selectCount(conn);

            if(totalArticleCount == 0){
                return new ArticleListModel();
            }

            int totalPageCount = calculateTotalPageCount(totalArticleCount);

            int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE + 1;
            int endRow = firstRow + COUNT_PER_PAGE - 1; // 자기 자신도 포함되니 하나를 빼주어야 한다.

            if(endRow > totalArticleCount){
                endRow = totalArticleCount;
            }

            List<Article> articleList = articleDao.select(conn, firstRow, endRow);
            ArticleListModel articleListModel = new ArticleListModel(
                    articleList, requestPageNumber, totalPageCount, firstRow, endRow);
            return  articleListModel;
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    /*
    전체 게시글 개수로부터 전체 페이지 개수를 구해주는 메서드
     */
    private int calculateTotalPageCount(int totalArticleCount){

        if(totalArticleCount == 0)  return 0;

        // 총 게시글 : 31 / 페이지 당 글 : 10 일때
        int pageCount = totalArticleCount / COUNT_PER_PAGE;         // pageCount : 3
        if(totalArticleCount % COUNT_PER_PAGE > 0) {
            pageCount++;                                            // 나머지가 1이므로 pageCount : 4
        }

        return pageCount;
    }
}
