<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-25
  Time: 오전 1:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>수정하기</title>
</head>
<body>

<form action="update.do" method="post">
  <input type="hidden" name="commentId" value="${comment.id}"/>
  <input type="hidden" name="articleId" value="${param.articleId}">
  <input type="hidden" name="p" value="${param.p}"/>
  작성자 : ${comment.writerName} <br/>
  암호 : <input type="password" name="password"/><br/>
  내용 : <br/>
  <textarea name="content" cols="40" rows="5" >${comment.content}</textarea>
  <br/>
  <input type="submit" value="전송"/>
</form>

</body>
</html>
