<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-18
  Time: 오후 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.service.ListArticleService" %>
<%@ page import="net.devgrus.board.model.ArticleListModel" %>
<%
  String pageNumberString = request.getParameter("p");
  int pageNumber = 1;
  if(pageNumberString != null && pageNumberString.length() > 0){
    pageNumber = Integer.parseInt(pageNumberString);
  }

  ListArticleService listService = ListArticleService.getInstance();
  ArticleListModel articleListModel = listService.getArticleList(pageNumber);
  request.setAttribute("listModel", articleListModel);

  if(articleListModel.getTotalPageCount() > 0){
    int beginPageNumber = (articleListModel.getRequestPage() - 1) / 10 * 10 + 1;
    int endPageNumber = beginPageNumber + 9;
    if(endPageNumber > articleListModel.getTotalPageCount()){
      endPageNumber = articleListModel.getTotalPageCount();
    }

    request.setAttribute("beginPage", beginPageNumber);
    request.setAttribute("endPage", endPageNumber);
  }
%>
<jsp:forward page="list_view.jsp"/>