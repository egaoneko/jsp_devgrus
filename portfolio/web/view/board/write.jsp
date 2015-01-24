<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-24
  Time: 오후 3:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>게시글 작성</title>
</head>
<body>
게시글을 등록했습니다.
<br/>
<a href="<c:url value="/board/list.do" />">목록보기</a>
<a href="<c:url value="/board/read.do?articleId=${postedArticle.id}"/> ">게시글 읽기</a>
</body>
</html>
