<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%@ page import="net.devgrus.board.service.UpdateArticleService" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="updateRequest" class="net.devgrus.board.service.UpdateRequest"/>
<jsp:setProperty name="updateRequest" property="*"/>
<%
  String viewPage = null;
  try{
    Article article = UpdateArticleService.getInstance().update(updateRequest);
    request.setAttribute("updatedArticle", article);
    viewPage = "/board/update_success.jsp";
  } catch (Exception ex){
    request.setAttribute("updateException", ex);
    viewPage = "/board/update_error.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>
