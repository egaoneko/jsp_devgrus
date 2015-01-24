package net.devgrus.board.handler;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import net.devgrus.board.dao.ArticleDao;
import net.devgrus.board.model.Article;
import net.devgrus.handler.CommandHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-22
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReplyFormHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        int parentArticleId = Integer.parseInt(request.getParameter("parentId"));

        Connection conn = null;
        try {
            conn = ConnectionProvider.getConnection();

            ArticleDao articleDao = ArticleDao.getInstance();
            Article parent = articleDao.selectById(conn, parentArticleId);

            try {
                checkParent(parent, parentArticleId);
            } catch (Exception e) {
                if (e instanceof ArticleNotFoundException) {
                    throw (ArticleNotFoundException) e;
                } else if (e instanceof CannotReplyArticleException) {
                    throw (CannotReplyArticleException) e;
                } else if (e instanceof LastChildAleadyExistsException) {
                    throw (LastChildAleadyExistsException) e;
                }
            }

            String searchMaxSeqNum = parent.getSequenceNumber();
            String searchMinSeqNum = getSearchMinSeqNum(parent);

            String lastChildSeq = articleDao.selectLastSequenceNumber(conn, searchMaxSeqNum, searchMinSeqNum);
            String sequenceNumber = getSequenceNumber(parent, lastChildSeq);

            return "/view/board/replyForm.jsp";
        } catch (SQLException e){
            throw new RuntimeException("DB 에러 : " + e.getMessage(), e);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    /*
    부모 글이 올바른지의 여부를 검사
     */
    private void checkParent(Article parent, int parentId) throws ArticleNotFoundException, CannotReplyArticleException{
        if(parent == null){
            throw new ArticleNotFoundException("부모글이 존재하지 않음 : " + parentId);
        }

        int parentLevel = parent.getLevel();
        if(parentLevel == 3){
            throw new CannotReplyArticleException("마지막 레벨 글에는 답글을 달 수 없습니다 : " + parent.getId());
        }
    }

    /*
    최소 순서 번호를 반환한다.
     */
    private String getSearchMinSeqNum(Article parent){
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
    private String getSequenceNumber(Article parent, String lastChildSeq) throws  LastChildAleadyExistsException{
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
