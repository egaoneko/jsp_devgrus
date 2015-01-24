<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 4:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.service.DeleteArticleService" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="deleteRequest" class="net.devgrus.board.service.DeleteRequest"/>
<jsp:setProperty name="deleteRequest" property="*"/>
<%
  String viewPage = null;
  try{
    DeleteArticleService.getInstance().deleteArticle(deleteRequest);
    viewPage = "/board/delete_success.jsp";
  } catch (Exception ex){
    request.setAttribute("deleteException", ex);
    viewPage = "/board/delete_error.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>
