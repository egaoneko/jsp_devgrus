package net.devgrus.comment.dao;

import jdbc.JdbcUtil;
import net.devgrus.comment.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class CommentDao {
    private static CommentDao instance = new CommentDao();
    public static CommentDao getInstance(){
        return instance;
    }

    private CommentDao(){}

    /*
    해당 게시글의 댓글 전체 개수를 구한다.
     */
    public int selectCount(Connection conn, int articleId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM portfolio_comment WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    댓글을 읽어온다.
     */
    public List<Comment> select(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT comment_id, group_id, sequence_no, posting_date, " +
                    "writer_name, password, content, article_id FROM portfolio_comment " +
                    "WHERE article_id = ? ORDER BY sequence_no DESC");
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();

            if(!rs.next()){
                return Collections.emptyList();
            }

            List<Comment> commentList = new ArrayList<Comment>();
            do{
                Comment comment = makeCommentFromResultSet(rs);
                commentList.add(comment);
            } while (rs.next());

            return commentList;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    ResultSet 으로부터 데이터를 읽어와 Comment 객체를 생성해준다.
     */
    private Comment makeCommentFromResultSet(ResultSet rs) throws SQLException{
        Comment comment = new Comment();

        comment.setId(rs.getInt("comment_id"));
        comment.setGroupId(rs.getInt("group_id"));
        comment.setSequenceNumber(rs.getString("sequence_no"));
        comment.setPostingDate(rs.getTimestamp("posting_date"));
        comment.setWriterName(rs.getString("writer_name"));
        comment.setPassword(rs.getString("password"));
        comment.setContent(rs.getString("content"));

        return comment;
    }

    /*
    댓글을 작성한다.
     */
    public int insert(Connection conn, Comment comment) throws SQLException{
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("INSERT INTO portfolio_comment " +
                    "(group_id, sequence_no, posting_date, writer_name, password, content, article_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, comment.getGroupId());
            pstmt.setString(2, comment.getSequenceNumber());
            pstmt.setTimestamp(3, new Timestamp(comment.getPostingDate().getTime()));
            pstmt.setString(4, comment.getWriterName());
            pstmt.setString(5, comment.getPassword());
            pstmt.setString(6, comment.getContent());
            pstmt.setInt(7, comment.getArticleId());
            int insertedCount = pstmt.executeUpdate();

            if(insertedCount > 0){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id() FROM portfolio_comment");
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
            return -1;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    댓글 수 증가시킨다.
     */
    public void increaseCommentCount(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement("UPDATE portfolio_article SET comment_count = comment_count + 1 " +
                    "WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    /*
    특정한 ID 값을 사용해 해당 글을 조회한다.
     */
    public Comment selectById(Connection conn, int commentId) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT * FROM portfolio_comment WHERE comment_id = ?");
            pstmt.setInt(1, commentId);
            rs = pstmt.executeQuery();

            if(!rs.next()){
                return null;
            }
            Comment comment = makeCommentFromResultSet(rs);
            return comment;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    마지막 순서 번호를 조회한다.
     */
    public String selectLastSequenceNumber(Connection conn,
                                           String searchMaxSeqNum, String searchMinSeqNum) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT min(sequence_no) FROM portfolio_comment " +
                    "WHERE sequence_no < ? and sequence_no >= ?");
            pstmt.setString(1, searchMaxSeqNum);
            pstmt.setString(2, searchMinSeqNum);
            rs = pstmt.executeQuery();

            if(!rs.next()){
                return null;
            }
            return rs.getString(1);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    글을 수정한다.
     */
    public int update(Connection conn, Comment comment) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPDATE portfolio_comment " +
                    "SET content = ? WHERE comment_id = ?");
            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getId());
            return pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    /*
    글을 삭제한다.
     */
    public void delete(Connection conn, int commentId) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("DELETE FROM portfolio_comment WHERE comment_id = ?");
            pstmt.setInt(1, commentId);
            pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    /*
    댓글 수 감소시킨다.
     */
    public void decreaseCommentCount(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement("UPDATE portfolio_article SET comment_count = comment_count - 1 " +
                    "WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }
}
