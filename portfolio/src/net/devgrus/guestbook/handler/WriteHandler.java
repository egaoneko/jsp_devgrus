package net.devgrus.guestbook.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.guestbook.dao.GuestbookDao;
import net.devgrus.guestbook.model.Guestbook;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WriteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        Guestbook guestbook = new Guestbook();
        guestbook.setGuestName(request.getParameter("guestName"));
        guestbook.setPassword(request.getParameter("password"));
        guestbook.setMessage(request.getParameter("message"));

        GuestbookDao guestbookDao = GuestbookDao.getInstance();
        Connection conn = null;
        try{
            conn = ConnectionProvider.getConnection();
            guestbookDao.insert(conn, guestbook);

            return "/view/guestbook/write.jsp";
        } finally {
            JdbcUtil.close(conn);
        }
    }
}
