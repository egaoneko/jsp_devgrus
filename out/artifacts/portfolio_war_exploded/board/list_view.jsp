<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-18
  Time: 오후 10:32
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
  <title>게시글 목록</title>
</head>
<body>
<table border="1">
  <c:if test="${listModel.totalPageCount > 0}">
    <tr>
      <td colspan="5">
          ${listModel.startRow}-${listModel.endRow}
        [${listModel.requestPage}/${listModel.totalPageCount}]
      </td>
    </tr>
  </c:if>

  <tr>
    <td>글 번호</td>
    <td>제목</td>
    <td>작성자</td>
    <td>작성일</td>
    <td>조회수</td>
  </tr>

  <c:choose>
    <c:when test="${listModel.hasArticle == false}">
      <tr>
        <td colspan="5">
          게시글이 없습니다.
        </td>
      </tr>
    </c:when>

    <c:otherwise>
      <c:forEach var="article" items="${listModel.articleList}">
        <tr>
          <td>${article.id}</td>
          <td>
            <c:if test="${article.level > 0}">
              <c:forEach begin="1" end="${article.level}">-</c:forEach>&gt;
            </c:if>
            <c:set var="query" value="articleId=${article.id}&p=${listModel.requestPage}"/>
            <a href="<c:url value="/board/read.jsp?${query}"/> ">
                ${article.title} (${article.commentCount})
            </a>
          </td>
          <td>${article.writerName}</td>
          <td>${article.postingDate}</td>
          <td>${article.readCount}</td>
        </tr>
      </c:forEach>

      <tr>
        <td colspan="5">
          <c:if test="${beginPage > 10}">
            <a href="<c:url value="/board/list.jsp?p=${beginPage-1}"/> ">이전</a>
          </c:if>
          <c:forEach var="pno" begin="${beginPage}" end="${endPage}">
            <a href="<c:url value="/board/list.jsp?p=${pno}"/> ">[${pno}]</a>
          </c:forEach>
          <c:if test="${endPage < listModel.totalPageCount}">
            <a href="<c:url value="/board/list.jsp?p=${endPage + 1}"/> ">다음</a>
          </c:if>
        </td>
      </tr>
    </c:otherwise>
  </c:choose>

  <tr>
    <td colspan="5">
      <a href="/board/writeForm.jsp">글쓰기</a>
    </td>
  </tr>
</table>

</body>
</html>