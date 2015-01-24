<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-24
  Time: 오후 5:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>답변쓰기</title>
</head>
<body>

<form action="reply.do" method="post">
  <input type="hidden" name="parentArticleId" value="${param.parentId}" />
  제목 : <input type="text" name="title" size="20" value="re: "/><br/>
  작성자 : <input type="text" name="writerName"/><br/>
  글암호 : <input type="password" name="password"/><br/>
  글내용 : <br/>
  <textarea name="content" cols="40" rows="5" ></textarea>
  <br/>
  <input type="submit" value="전송"/>
</form>

</body>
</html>
