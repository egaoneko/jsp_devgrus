package net.devgrus.comment.service;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.comment.dao.CommentDao;
import net.devgrus.comment.model.Comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-04
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReplyCommentService {

    private static ReplyCommentService instance = new ReplyCommentService();
    public static ReplyCommentService getInstance(){
        return instance;
    }

    private ReplyCommentService(){}

    public Comment reply(ReplyingRequest replyingRequest) throws CommentNotFoundException,
            CannotReplyCommentException, LastChildAleadyExistsException {
        Connection conn = null;

        Comment comment = replyingRequest.toComment();

        try{
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);

            CommentDao commentDao = CommentDao.getInstance();
            Comment parent = commentDao.selectById(conn, replyingRequest.getParentCommentId());

            try {
                checkParent(parent, replyingRequest.getParentCommentId());
            } catch (Exception e){
                JdbcUtil.rollback(conn);
                if(e instanceof CommentNotFoundException){
                    throw (CommentNotFoundException)e;
                } else if(e instanceof CannotReplyCommentException){
                    throw (CannotReplyCommentException)e;
                } else if(e instanceof LastChildAleadyExistsException){
                    throw (LastChildAleadyExistsException)e;
                }
            }

            String searchMaxSeqNum = parent.getSequenceNumber();
            String searchMinSeqNum = getSearchMinSeqNum(parent);

            String lastChildSeq = commentDao.selectLastSequenceNumber(conn, searchMaxSeqNum, searchMinSeqNum);
            String sequenceNumber = getSequenceNumber(parent, lastChildSeq);

            comment.setGroupId(parent.getGroupId());
            comment.setSequenceNumber(sequenceNumber);
            comment.setPostingDate(new Date());

            int articleId = commentDao.insert(conn,comment);
            if(articleId == -1){
                throw new RuntimeException("DB 삽입 실패 : " + articleId);
            }
            conn.commit();

            commentDao.increaseCommentCount(conn, comment.getArticleId());

            comment.setId(articleId);
            return comment;
        } catch (SQLException e){
            JdbcUtil.rollback(conn);
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
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

    /*
    부모 글이 올바른지의 여부를 검사
     */
    private void checkParent(Comment parent, int parentId) throws CommentNotFoundException, CannotReplyCommentException{
        if(parent == null){
            throw new CommentNotFoundException("부모글이 존재하지 않음 : " + parentId);
        }

        int parentLevel = parent.getLevel();
        if(parentLevel == 3){
            throw new CannotReplyCommentException("마지막 레벨 글에는 답글을 달 수 없습니다 : " + parent.getId());
        }
    }

    /*
    최소 순서 번호를 반환한다.
     */
    private String getSearchMinSeqNum(Comment parent){
        String parentSeqNum = parent.getSequenceNumber();
        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        long parentSeqLongValue = Long.parseLong(parentSeqNum);
        long searchMinLongValue = 0;
        switch (parent.getLevel()){                                             // 0000009999 654321
            case 0:
                searchMinLongValue = parentSeqLongValue / 1000000L * 1000000L;  // 0000009999 000000
                break;
            case 1:
                searchMinLongValue = parentSeqLongValue / 10000L * 10000L;      // 0000009999 650000
                break;
            case 2:
                searchMinLongValue = parentSeqLongValue / 100L * 100L;          // 0000009999 654300
                break;
        }
        return decimalFormat.format(searchMinLongValue);
    }

    /*
    순서 번호를 생성한다.
     */
    private String getSequenceNumber(Comment parent, String lastChildSeq) throws LastChildAleadyExistsException {
        long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
        int parentLevel = parent.getLevel();

        long decUnit = 0;
        if(parentLevel == 0){
            decUnit = 10000L;
        } else if(parentLevel == 1){
            decUnit = 100L;
        } else if(parentLevel ==2){
            decUnit = 1L;
        }

        String sequenceNumber = null;

        DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
        if(lastChildSeq == null){   // 답변글이 없음
            sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
        } else {    // 답변글이 있음
            // 마지막 답변글인지 확인
            String orderOfLastChildSeq = null;
            if(parentLevel == 0){
                orderOfLastChildSeq = lastChildSeq.substring(10, 12);       // 0000000000 00 0000
                sequenceNumber = lastChildSeq.substring(0, 12) + "9999";    // 000000000000 + 9999
            } else if(parentLevel == 1){
                orderOfLastChildSeq = lastChildSeq.substring(12, 14);       // 000000000000 00 00
                sequenceNumber = lastChildSeq.substring(0, 14) + "99";      // 00000000000000 + 99
            } else if(parentLevel == 2){
                orderOfLastChildSeq = lastChildSeq.substring(14, 16);       // 00000000000000 00
                sequenceNumber = lastChildSeq;
            }
            if(orderOfLastChildSeq.equals("00")){
                throw new LastChildAleadyExistsException("마지막 자식글이 이미 존재합니다 : " + lastChildSeq);
            }
            long seq = Long.parseLong(sequenceNumber) - decUnit;
            sequenceNumber = decimalFormat.format(seq);
        }
        return sequenceNumber;
    }
}

