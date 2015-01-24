<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.service.ReplyArticleService" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="replyingRequest" class="net.devgrus.board.service.ReplyingRequest" />
<jsp:setProperty name="replyingRequest" property="*" />
<%
  String viewPage = null;
  try {
    Article postedArticle = ReplyArticleService.getInstance().reply(replyingRequest);
    request.setAttribute("postedArticle", postedArticle);
    viewPage = "/board/reply_success.jsp";
  } catch (Exception ex){
    viewPage = "/board/reply_error.jsp";
    request.setAttribute("replyException", ex);
  }
%>
<jsp:forward page="<%= viewPage %>"/>