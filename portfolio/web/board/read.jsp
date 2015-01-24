<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%@ page import="net.devgrus.board.service.ReadArticleService" %>
<%@ page import="net.devgrus.board.service.ArticleNotFoundException" %>
<%
  int articleId = Integer.parseInt(request.getParameter("articleId"));
  String viewPage = null;
  try{
    Article article = ReadArticleService.getInstance().readArticle(articleId);
    request.setAttribute("article", article);
    viewPage = "/board/read_view.jsp";
  } catch (ArticleNotFoundException ex){
    viewPage = "/board/article_not_found.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>
