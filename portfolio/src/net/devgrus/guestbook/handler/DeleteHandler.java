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

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class DeleteHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int guestbookId = Integer.parseInt(request.getParameter("guestbookId"));
        String password = request.getParameter("password");

        GuestbookDao guestbookDao = GuestbookDao.getInstance();
        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            Guestbook guestbook = guestbookDao.select(conn, guestbookId);

            if(guestbook == null){
                throw new GuestbookNotFoundException("방명록이 없음 : " + guestbook);
            }

            if(!guestbook.hasPassword()) {
                throw new InvalidGuestbookPasswordException("비밀번호가 없음");
            }
            if(!guestbook.getPassword().equals(password)){
                throw new InvalidGuestbookPasswordException("비밀번호가 틀림");
            }

            guestbookDao.delete(conn, guestbookId);
            conn.commit();

            return "/view/guestbook/delete.jsp";
        } catch (SQLException ex){
            JdbcUtil.rollback(conn);
            throw new GuestbookException("삭제 처리 중 에러 발생 : " + ex.getMessage(), ex);
        } catch (InvalidGuestbookPasswordException ex){
            JdbcUtil.rollback(conn);
            throw ex;
        } catch (GuestbookNotFoundException ex){
            JdbcUtil.rollback(conn);
            throw ex;
        } finally {
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e){
                }
            }
            JdbcUtil.close(conn);
        }
    }
}
