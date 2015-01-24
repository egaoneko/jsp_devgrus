package net.devgrus.board.service;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-19
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ReplyingRequest extends WritingRequest {

    private int parentArticleId;

    public int getParentArticleId() {
        return parentArticleId;
    }

    public void setParentArticleId(int parentArticleId) {
        this.parentArticleId = parentArticleId;
    }
}
