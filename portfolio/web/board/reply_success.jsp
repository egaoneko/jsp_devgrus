<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 2:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>답변글 작성</title>
</head>
<body>
답변글을 등록했습니다.
<br/>
<a href="list.jsp?p=${param.p}">목록보기</a>
<a href="read.jsp?articleId=${postedArticle.id}&p=${param.p}">게시글 읽기</a>

</body>
</html>

