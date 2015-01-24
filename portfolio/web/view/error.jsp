<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-24
  Time: 오후 5:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>에러</title></head>
<body>
${error}
<br/>
<a href="<c:url value='list.do'/>">목록보기</a>
</body>
</html>
