package net.devgrus.comment.model;

import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class CommentListModel {

    private List<Comment> commentList;  // 화면에 보여줄 댓글 목록
    private int requestArticle;         // 사용자가 요청한 글 번호


    public CommentListModel() {
        this(Collections.<Comment>emptyList(), 0);
    }

    public CommentListModel(List<Comment> commentList, int requestArticle){
        this.commentList = commentList;
        this.requestArticle = requestArticle;
    }

    /*
    댓글이 존재하는지 확인
     */
    public boolean isHasComment() {
        return ! commentList.isEmpty();
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getRequestArticle() {
        return requestArticle;
    }

    public void setRequestArticle(int requestArticle) {
        this.requestArticle = requestArticle;
    }
}

