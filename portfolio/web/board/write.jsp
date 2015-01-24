<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.board.model.Article" %>
<%@ page import="net.devgrus.board.service.WriteArticleService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="writingRequest" class="net.devgrus.board.service.WritingRequest" />
<jsp:setProperty name="writingRequest" property="*"/>
<%
  Article postedArticle = WriteArticleService.getInstance().write(writingRequest);
  request.setAttribute("postedArticle", postedArticle);
%>
<html>
<head>
  <title>게시글 작성</title>
</head>
<body>
게시글을 등록했습니다.
<br/>
<a href="<c:url value="/board/list.jsp" />">목록보기</a>
<a href="<c:url value="/board/read.jsp?articleId=${postedArticle.id}"/> ">게시글 읽기</a>
</body>
</html>

