package net.devgrus.board.model;

import java.util.Collections;
import java.util.List;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-18
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class ArticleListModel {

    private List<Article> articleList;  // 화면에 보여줄 게시글 목록
    private int requestPage;            // 사용자가 요청한 페이지 번호
    private int totalPageCount;         // 전체 페이지 개수
    private int startRow;               // 현재 게시글 목록의 시작 행(전체 게시글 기준)
    private int endRow;                 // 현재 게시글 목록의 끝 행(전체 게시글 기준)

    public ArticleListModel() {
        this(Collections.<Article>emptyList(), 0, 0, 0, 0);
    }

    public ArticleListModel(List<Article> articleList, int requestPage, int totalPageCount, int startRow, int endRow){
        this.articleList = articleList;
        this.requestPage = requestPage;
        this.totalPageCount = totalPageCount;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    /*
    게시글이 존재하는지 확인
     */
    public boolean isHasArticle(){
        return !articleList.isEmpty();
    }

    public int getRequestPage() {
        return requestPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }
}
