package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
import net.devgrus.board.model.ArticleListModel;
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
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ListHandler implements CommandHandler {

    public static final int COUNT_PER_PAGE = 10;

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        String requestPageNumberString = request.getParameter("p");

        if(requestPageNumberString == null || requestPageNumberString.equals("")) {
            requestPageNumberString = "1";
        }

        int requestPageNumber = Integer.parseInt(requestPageNumberString);

        if(requestPageNumber < 0){
            throw new IllegalArgumentException("page number < 0 : " + requestPageNumber);
        }

        ArticleDao articleDao = ArticleDao.getInstance();
        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            int totalArticleCount = articleDao.selectCount(conn);

            if(totalArticleCount == 0){
                setListAttributes(request,response);
                return "/view/board/list.jsp";
            }

            int totalPageCount = calculateTotalPageCount(totalArticleCount);

            int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE + 1;
            int endRow = firstRow + COUNT_PER_PAGE - 1; // 자기 자신도 포함되니 하나를 빼주어야 한다.

            if(endRow > totalArticleCount){
                endRow = totalArticleCount;
            }

            List<Article> articleList = articleDao.select(conn, firstRow, endRow);
            setListAttributes(request, response, articleList, requestPageNumber, totalPageCount, firstRow, endRow);
            return "/view/board/list.jsp";
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

    /*
    List 을 생성하는데 필요한 자원들을 속성에 저장한다. 이 메소드는 게시글이 없을 때 수행된다.
     */
    private void setListAttributes(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        request.setAttribute("requestPage", new Integer(0));
        request.setAttribute("totalPageCount", new Integer(0));
        request.setAttribute("startRow", new Integer(0));
        request.setAttribute("endRow", new Integer(0));
        request.setAttribute("articleList", Collections.<Article>emptyList());
        request.setAttribute("hasArticle", new Boolean(false));
    }

    /*
    List 을 생성하는데 필요한 자원들을 속성에 저장한다.
     */
    private void setListAttributes(HttpServletRequest request, HttpServletResponse response,
                                   List<Article> articleList, int requestPage, int totalPageCount,
                                   int startRow, int endRow) throws Throwable {

        request.setAttribute("requestPage", new Integer(requestPage));
        request.setAttribute("totalPageCount", new Integer(totalPageCount));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("articleList", articleList);
        request.setAttribute("hasArticle", new Boolean(true));

        int beginPageNumber = (requestPage - 1) / COUNT_PER_PAGE * COUNT_PER_PAGE + 1;
        int endPageNumber = beginPageNumber + COUNT_PER_PAGE - 1;
        if(endPageNumber > totalPageCount){
            endPageNumber = totalPageCount;
        }
        request.setAttribute("beginPage", beginPageNumber);
        request.setAttribute("endPage", endPageNumber);
    }
}
