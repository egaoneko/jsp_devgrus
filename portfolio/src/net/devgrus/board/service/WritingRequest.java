package net.devgrus.board.service;

import net.devgrus.board.model.Article;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-19
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class WritingRequest {

    private String writerName;
    private String password;
    private String title;
    private String content;

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

    public Article toArticle() {
        Article article = new Article();
        article.setWriterName(writerName);
        article.setPassword(password);
        article.setTitle(title);
        article.setContent(content);

        return article;
    }
}
