package net.devgrus.guestbook.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.guestbook.dao.GuestbookDao;
import net.devgrus.guestbook.model.Guestbook;
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
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ListHandler implements CommandHandler {

    private static final int COUNT_PER_PAGE = 10;

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

        GuestbookDao guestbookDao = GuestbookDao.getInstance();
        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();

            int guestTotalCount = guestbookDao.selectCount(conn);
            int totalPageCount = calculateTotalPageCount(guestTotalCount);

            List<Guestbook> guestbookList = null;
            int startRow = 0;
            int endRow = 0;
            if (guestTotalCount > 0){
                startRow = (requestPageNumber - 1) * COUNT_PER_PAGE + 1;
                endRow = startRow + COUNT_PER_PAGE - 1;
                guestbookList = guestbookDao.selectList(conn, startRow, endRow);

                int beginPageNumber = (requestPageNumber - 1) / COUNT_PER_PAGE * COUNT_PER_PAGE + 1;
                int endPageNumber = beginPageNumber + COUNT_PER_PAGE - 1;
                if(endPageNumber > totalPageCount){
                    endPageNumber = totalPageCount;
                }
                request.setAttribute("beginPage", beginPageNumber);
                request.setAttribute("endPage", endPageNumber);

            } else {
                requestPageNumber = 0;
                guestbookList = Collections.emptyList();
            }

            request.setAttribute("requestPage", new Integer(requestPageNumber));
            request.setAttribute("totalPageCount", new Integer(totalPageCount));
            request.setAttribute("guestTotalCount", new Integer(guestTotalCount));
            request.setAttribute("startRow", new Integer(startRow));
            request.setAttribute("endRow", new Integer(endRow));
            request.setAttribute("guestbookList", guestbookList);


            return "/view/guestbook/list.jsp";
        } catch (SQLException e){
            throw new GuestbookException("메시지 목록 구하기 실패 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    /*
    전체 방명록 개수로부터 전체 페이지 개수를 구해주는 메서드
     */
    private int calculateTotalPageCount(int guestTotalCount){

        if(guestTotalCount == 0)  return 0;

        // 총 게시글 : 31 / 페이지 당 글 : 10 일때
        int pageCount = guestTotalCount / COUNT_PER_PAGE;         // pageCount : 3
        if(guestTotalCount % COUNT_PER_PAGE > 0) {
            pageCount++;                                            // 나머지가 1이므로 pageCount : 4
        }

        return pageCount;
    }
}
