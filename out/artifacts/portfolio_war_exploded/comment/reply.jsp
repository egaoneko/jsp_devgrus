<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-04
  Time: 오전 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.comment.service.ReplyCommentService" %>
<%@ page import="net.devgrus.comment.model.Comment" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="replyingRequest" class="net.devgrus.comment.service.ReplyingRequest" />
<jsp:setProperty name="replyingRequest" property="*" />
<%
  String viewPage = null;
  try {
    Comment postedComment = ReplyCommentService.getInstance().reply(replyingRequest);
    request.setAttribute("postedComment", postedComment);
    viewPage = "/comment/reply_success.jsp";
  } catch (Exception ex){
    viewPage = "/comment/reply_error.jsp";
    request.setAttribute("replyException", ex);
  }
%>
<jsp:forward page="<%= viewPage %>"/>