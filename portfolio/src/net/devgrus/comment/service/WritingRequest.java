package net.devgrus.comment.service;

import net.devgrus.comment.model.Comment;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-03
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WritingRequest {

    private String writerName;
    private String password;
    private String content;
    private int articleId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Comment toComment() {
        Comment comment = new Comment();
        comment.setWriterName(writerName);
        comment.setPassword(password);
        comment.setContent(content);
        comment.setArticleId(articleId);

        return comment;
    }
}
