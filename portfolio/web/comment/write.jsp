<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-03
  Time: 오전 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.devgrus.comment.service.WriteCommentService" %>
<%@ page import="net.devgrus.comment.model.Comment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="writingRequest" class="net.devgrus.comment.service.WritingRequest" />
<jsp:setProperty name="writingRequest" property="*"/>
<%
  Comment postedComment = WriteCommentService.getInstance().write(writingRequest);
  request.setAttribute("postedComment", postedComment);
%>
<html>
<head>
    <title>댓글 작성</title>
</head>
<body>
댓글을 등록했습니다.
<br/>
<a href="<c:url value="/board/list.jsp" />">목록보기</a>
<a href="<c:url value="/board/read.jsp?articleId=${postedComment.articleId}"/> ">게시글 읽기</a>
</body>
</html>
