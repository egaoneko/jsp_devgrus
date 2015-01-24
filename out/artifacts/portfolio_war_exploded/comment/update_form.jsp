<%@ page import="net.devgrus.comment.model.Comment" %>
<%@ page import="net.devgrus.comment.service.CommentNotFoundException" %>
<%@ page import="net.devgrus.comment.service.ReadCommentService" %>
<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-04
  Time: 오전 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String viewPage = null;
  try{
    int commentId = Integer.parseInt(request.getParameter("commentId"));
    Comment comment = ReadCommentService.getInstance().readComment(commentId);
    viewPage = "/comment/update_form_view.jsp";
    request.setAttribute("comment", comment);
  } catch (CommentNotFoundException ex){
    viewPage = "/comment/article_not_found.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>