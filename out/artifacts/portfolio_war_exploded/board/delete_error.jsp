<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-19
  Time: 오후 4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="exceptionType" value="${deleteException.getClass().simpleName}" />
<html>
<head>
  <title>삭제 실패</title>
</head>
<body>
에러 :
<c:choose>
  <c:when test="${exceptionType == 'ArticleNotFoundException'}">
    삭제할 게시글이 존재하지 않습니다.
  </c:when>
  <c:when test="${exceptionType == 'InvalidPasswordException'}">
    암호를 잘못 입력하셨습니다.
  </c:when>
</c:choose>
<br/>
<a href="<c:url value="/board/list.jsp"/> ">목록보기</a>
</body>
</html>
