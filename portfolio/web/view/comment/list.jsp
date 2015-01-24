<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-25
  Time: 오전 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${hasComment == false}">
    <div style="border: 1px solid; width: 50%;">
      <table>
        <tr>
          <td>
            댓글이 없습니다.
          </td>
        </tr>
      </table>
    </div>
  </c:when>

  <c:otherwise>

    <c:forEach var="comment" items="${commentList}" varStatus="status">
      <div style="border: 1px solid; margin-left: ${comment.level * 30}px; width: 50%;">
        <table>

          <tr>
            <td>작성자</td>
            <td>${comment.writerName}</td>
          </tr>

          <tr>
            <td>작성일</td>
            <td>${comment.postingDate}</td>
          </tr>

          <tr>
            <td>내용</td>
            <td>
            <pre>
              <pre><c:out value="${comment.content}"/></pre>

              </pre>
            </td>

          </tr>
          <tr>
            <td colspan="2">
              <a href="<c:url value="/comment/replyForm.do?articleId=${article.id}&parentId=${comment.id}&p=${param.p}"/>">답변하기</a>
              <a href="<c:url value="/comment/updateForm.do?articleId=${article.id}&commentId=${comment.id}&p=${param.p}"/> ">수정하기</a>
              <a href="<c:url value="/comment/deleteForm.do?articleId=${article.id}&commentId=${comment.id}"/> ">삭제하기</a>
            </td>
          </tr>

        </table>
      </div>

      <br/>

    </c:forEach>

  </c:otherwise>
</c:choose>

<div style="border: 2px solid; margin: 30px 10px; width: 50%;">
  <table>
    <tr>
      <td>
        <form action="<c:url value="/comment/write.do" />" method="post">
        <input type="hidden" name="articleId" value="${article.id}">
        작성자 : <input type="text" name="writerName"/><br/>
        암호: <input type="password" name="password"/><br/>
        내용: <br/>
        <textarea name="content" cols="40" rows="5"></textarea>
        <br/>
        <input type="submit" value="전송">
        </form>
      </td>
    </tr>
  </table>
</div>