<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-04
  Time: 오전 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%@ page import="net.devgrus.comment.service.UpdateCommentService" %>
<%@ page import="net.devgrus.comment.model.Comment" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="updateRequest" class="net.devgrus.comment.service.UpdateRequest"/>
<jsp:setProperty name="updateRequest" property="*"/>
<%
  String viewPage = null;
  try{
    Comment comment = UpdateCommentService.getInstance().update(updateRequest);
    request.setAttribute("updatedComment", comment);
    viewPage = "/comment/update_success.jsp";
  } catch (Exception ex){
    request.setAttribute("updateException", ex);
    viewPage = "/comment/update_error.jsp";
  }
%>
<jsp:forward page="<%= viewPage %>"/>
