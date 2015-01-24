package net.devgrus.board.model;

import java.util.Date;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class Article {

    private int id;
    private int groupId;
    private String sequenceNumber;
    private Date postingDate;
    private int readCount;
    private String writerName;
    private String password;
    private String title;
    private String content;
    private int commentCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    /*
    중첩 레벨을 구해주는 메서드
     */
    public int getLevel(){
        if(sequenceNumber == null)          return -1;
        if(sequenceNumber.length() != 16)   return -1;

        if(sequenceNumber.endsWith("999999"))   return 0;   // 루트
        if(sequenceNumber.endsWith("9999"))     return 1;   // 첫 번째 자식
        if(sequenceNumber.endsWith("99"))       return 2;   // 두 번째 자식
        return 3;                                           // 세 번째 자식
    }
}
