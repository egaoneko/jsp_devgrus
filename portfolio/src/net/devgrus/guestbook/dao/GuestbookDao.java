package net.devgrus.guestbook.dao;

import jdbc.JdbcUtil;
import net.devgrus.guestbook.model.Guestbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-26
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class GuestbookDao {
    private static GuestbookDao instance = new GuestbookDao();
    public static GuestbookDao getInstance() {
        return instance;
    }

    public GuestbookDao() {
    }

    public int insert(Connection conn, Guestbook guestbook) throws SQLException {
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("INSERT INTO portfolio_guestbook(guest_name, password, message) " +
                    "VALUES (?, ?, ?)");
            pstmt.setString(1, guestbook.getGuestName());
            pstmt.setString(2, guestbook.getPassword());
            pstmt.setString(3, guestbook.getMessage());
            return pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    public Guestbook select(Connection conn, int geustbookId) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM portfolio_guestbook WHERE guestbook_id = ?");
            pstmt.setInt(1, geustbookId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return makeGuestbookFromResultSet(rs);
            } else {
                return null;
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    private Guestbook makeGuestbookFromResultSet(ResultSet rs) throws SQLException{
        Guestbook guestbook = new Guestbook();
        guestbook.setId(rs.getInt("guestbook_id"));
        guestbook.setGuestName(rs.getString("guest_name"));
        guestbook.setPassword(rs.getString("password"));
        guestbook.setMessage(rs.getString("message"));
        return guestbook;
    }

    public int selectCount(Connection conn) throws SQLException{
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM portfolio_guestbook");
            rs.next();
            return rs.getInt(1);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
    }

    public List<Guestbook> selectList(Connection conn, int firstRow, int endRow) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement("SELECT * FROM portfolio_guestbook " +
                    "ORDER BY guestbook_id DESC LIMIT ?, ?");
            pstmt.setInt(1, firstRow - 1);
            pstmt.setInt(2, endRow - firstRow + 1);
            rs = pstmt.executeQuery();
            if(rs.next()){
                List<Guestbook> guestbookList = new ArrayList<Guestbook>();
                do{
                    guestbookList.add(makeGuestbookFromResultSet(rs));
                } while (rs.next());
                return guestbookList;
            } else {
                return Collections.emptyList();
            }
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    public int delete(Connection conn, int guestbookId) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("DELETE FROM portfolio_guestbook WHERE guestbook_id = ?");
            pstmt.setInt(1, guestbookId);
            return pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }
}
