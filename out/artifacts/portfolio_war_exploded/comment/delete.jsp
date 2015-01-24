<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-04
  Time: 오전 3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.comment.service.DeleteCommentService" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="deleteRequest" class="net.devgrus.comment.service.DeleteRequest"/>
<jsp:setProperty name="deleteRequest" property="*"/>
<%
  String viewPage = null;
  try{
    DeleteCommentService.getInstance().deleteComment(deleteRequest);
    viewPage = "/comment/delete_success.jsp";
  } catch (Exception ex){
    request.setAttribute("deleteException", ex);
    viewPage = "/comment/delete_error.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>
