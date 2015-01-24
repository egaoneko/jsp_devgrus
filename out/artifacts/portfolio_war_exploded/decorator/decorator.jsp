<%--
  Created by IntelliJ IDEA.
  User: SeoDong
  Date: 2015-01-20
  Time: 오후 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
<head>
    <title><decorator:title default="devgrus.net"/></title>
    <style type="text/css">
        #header {
            color: blue
        }

        #footer {
            color: red
        }
    </style>
    <decorator:head/>
</head>
<body>
<div id="header">상단 공통 메뉴</div>
<hr/>
<decorator:body/>
<hr/>
<div id="footer">하단 공통 메뉴</div>
</body>
</html>
