package net.devgrus.board.dao;

import jdbc.JdbcUtil;
import net.devgrus.board.model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ArticleDao {

    private static ArticleDao instance = new ArticleDao();
    public static ArticleDao getInstance(){
        return instance;
    }

    private ArticleDao(){}

    /*
    게시글의 전체 개수를 구한다.
     */
    public int selectCount(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM portfolio_article");
            rs.next();
            return rs.getInt(1);
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(stmt);
        }
    }

    /*
    전체 게시글 중에서 시작 행(firstRow)과 끝 행(endRow)에 속하는 게시글을 읽어온다.
     */
    public List<Article> select(Connection conn, int firstRow, int endRow) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT article_id, group_id, sequence_no, posting_date, " +
                    "read_count, writer_name, password, title, comment_count" +
                    " FROM portfolio_article ORDER BY sequence_no DESC  LIMIT ?, ?");
            pstmt.setInt(1, firstRow - 1);              // LIMIT 첫번 째 요소는 0 부터 시작한다.
            pstmt.setInt(2, endRow - firstRow + 1);     // LIMIT 두번 째 요소는 불러올 글의 개수이다. endRow - firstRow + 1 : 불러올 게시글 개수
            rs = pstmt.executeQuery();
            if(!rs.next()){
                return Collections.emptyList();
            }

            List<Article> articleList = new ArrayList<Article>();
            do{
                Article article = makeArticleFromResultSet(rs, false);
                articleList.add(article);
            } while (rs.next());

            return articleList;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    ResultSet 으로부터 데이터를 읽어와 Article 객체를 생성해준다.
    이때 readContent 의 값에 따라 content 칼럼의 값을 읽어올지의 여부를 결정한다.
     */
    private Article makeArticleFromResultSet(ResultSet rs, boolean readContent) throws SQLException{
        Article article = new Article();

        article.setId(rs.getInt("article_id"));
        article.setGroupId(rs.getInt("group_id"));
        article.setSequenceNumber(rs.getString("sequence_no"));
        article.setPostingDate(rs.getTimestamp("posting_date"));
        article.setReadCount(rs.getInt("read_count"));
        article.setWriterName(rs.getString("writer_name"));
        article.setPassword(rs.getString("password"));
        article.setTitle(rs.getString("title"));
        article.setCommentCount(rs.getInt("comment_count"));

        if(readContent){
            article.setContent(rs.getString("content"));
        }

        return article;
    }

    /*
    게시글을 작성한다.
     */
    public int insert(Connection conn, Article article) throws SQLException{
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("INSERT INTO portfolio_article " +
                    "(group_id, sequence_no, posting_date, read_count, writer_name, " +
                    "password, title, content, comment_count) VALUES " +
                    "(?, ?, ?, 0, ?, ?, ?, ?, 0)");
            pstmt.setInt(1, article.getGroupId());
            pstmt.setString(2, article.getSequenceNumber());
            pstmt.setTimestamp(3, new Timestamp(article.getPostingDate().getTime()));
            pstmt.setString(4, article.getWriterName());
            pstmt.setString(5, article.getPassword());
            pstmt.setString(6, article.getTitle());
            pstmt.setString(7, article.getContent());
            int insertedCount = pstmt.executeUpdate();

            if(insertedCount > 0){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id() FROM portfolio_article");
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
    특정한 ID 값을 사용해 해당 글을 조회한다.
     */
    public Article selectById(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            pstmt = conn.prepareStatement("SELECT * FROM portfolio_article WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();

            if(!rs.next()){
                return null;
            }
            Article article = makeArticleFromResultSet(rs, true);
            return article;
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
        }
    }

    /*
    조회수를 증가시킨다.
     */
    public void increaseReadCount(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;

        try{
            pstmt = conn.prepareStatement("UPDATE portfolio_article SET read_count = read_count + 1 " +
                    "WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } finally {
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
            pstmt = conn.prepareStatement("SELECT min(sequence_no) FROM portfolio_article " +
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
    public int update(Connection conn, Article article) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("UPDATE portfolio_article " +
                    "SET title = ?, content = ? WHERE article_id = ?");
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.setInt(3, article.getId());
            return pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }

    /*
    글을 삭제한다.
     */
    public void delete(Connection conn, int articleId) throws SQLException{
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("DELETE FROM portfolio_article WHERE article_id = ?");
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } finally {
            JdbcUtil.close(pstmt);
        }
    }
}
