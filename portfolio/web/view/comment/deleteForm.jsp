<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-25
  Time: 오전 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>삭제하기</title>
</head>
<body>

<form action="<c:url value="/comment/delete.do" />" method="post">
  <input type="hidden" name="articleId" value="${param.articleId}"/>
  <input type="hidden" name="commentId" value="${param.commentId}">
  암호 : <input type="password" name="password"/><br/>
  <input type="submit" value="삭제"/>
</form>

</body>
</html>

