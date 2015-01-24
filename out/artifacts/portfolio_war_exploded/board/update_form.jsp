<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.service.ArticleNotFoundException" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%@ page import="net.devgrus.board.service.ReadArticleService" %>
<%
  String viewPage = null;
  try{
    int articleId = Integer.parseInt(request.getParameter("articleId"));
    Article article = ReadArticleService.getInstance().getArticle(articleId);
    viewPage = "/board/update_form_view.jsp";
    request.setAttribute("article", article);
  } catch (ArticleNotFoundException ex){
    viewPage = "/board/article_not_found.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>

