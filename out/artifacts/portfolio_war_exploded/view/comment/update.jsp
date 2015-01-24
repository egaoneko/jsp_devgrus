<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-25
  Time: 오전 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>댓글 수정</title>
</head>
<body>
댓글을 수정했습니다.
<br/>
<a href="/board/list.do?p=${param.p}">목록보기</a>
<a href="/board/read.do?articleId=${param.articleId}&p=${param.p}">게시글 읽기</a>

</body>
</html>