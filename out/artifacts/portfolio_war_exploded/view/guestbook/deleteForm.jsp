<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-26
  Time: 오전 2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>방명록 메시지 삭제 확인</title>
</head>
<body>

<form action="delete.do" method="post">
  <input type="hidden" name="guestbookId" value="${param.guestbookId}" />
  메시지를 삭제하시려면 암호를 입력하세요:<br/>
  암호: <input type="password" name="password" /> <br />
  <input type="submit" value="메시지 삭제하기" />
</form>
</body>
</html>