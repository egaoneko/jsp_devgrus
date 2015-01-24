<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>글 수정</title>
</head>
<body>
글을 수정했습니다.
<br/>
<a href="list.jsp?p=${param.p}">목록보기</a>
<a href="read.jsp?articleId=${updatedArticle.id}&p=${param.p}">게시글 읽기</a>

</body>
</html>
