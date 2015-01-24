<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-25
  Time: 오전 1:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>답변쓰기</title>
</head>
<body>

<form action="reply.do" method="post">
  <input type="hidden" name="parentCommentId" value="${param.parentId}" />
  <input type="hidden" name="articleId" value="${param.articleId}">
  작성자 : <input type="text" name="writerName"/><br/>
  암호 : <input type="password" name="password"/><br/>
  내용 : <br/>
  <textarea name="content" cols="40" rows="5" ></textarea>
  <br/>
  <input type="submit" value="전송"/>
</form>

</body>
</html>
