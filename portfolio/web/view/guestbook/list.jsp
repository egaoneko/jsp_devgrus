<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-26
  Time: 오전 2:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    /*
    웹 브라우저가 게시글 목록을 캐싱하지 않도록 캐시 관련 헤더를 설정
     */
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expire", 1L);
%>

<html>
<head>
    <title>방명록 메시지 목록</title>
</head>
<body>

<form action="write.do" method="post">
    이름: <input type="text" name="guestName"/> <br/>
    암호: <input type="password" name="password"/> <br/>
    메시지: <textarea name="message" cols="30" row="3"></textarea> <br/>
    <input type="submit" value="메시지 남기기"/>
</form>
<hr>

<c:if test="${guestTotalCount <= 0}">
    등록된 메시지가 없습니다.
</c:if>

<c:if test="${guestTotalCount > 0}">
    <table border="1">
        <tr>
            <td>
                    ${startRow}-${endRow}
                [${requestPage}/${totalPageCount}]
            </td>
        </tr>

        <c:forEach var="guestbook" items="${guestbookList}">
            <tr>
                <td>
                    메시지 번호: ${guestbook.id} <br/>
                    손님 이름: ${guestbook.guestName} <br/>
                    메시지: ${guestbook.message} <br/>
                    <a href="deleteForm.do?guestbookId=${guestbook.id}">[삭제하기]</a>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td>
                <c:if test="${beginPage > 10}">
                    <a href="<c:url value="/guestbook/list.do?p=${beginPage-1}"/> ">이전</a>
                </c:if>
                <c:forEach var="pno" begin="${beginPage}" end="${endPage}">
                    <a href="<c:url value="/guestbook/list.do?p=${pno}"/> ">[${pno}]</a>
                </c:forEach>
                <c:if test="${endPage < totalPageCount}">
                    <a href="<c:url value="/guestbook/list.do?p=${endPage + 1}"/> ">다음</a>
                </c:if>
            </td>
        </tr>
    </table>
</c:if>
</body>
</html>

