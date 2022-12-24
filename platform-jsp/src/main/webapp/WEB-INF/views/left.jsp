<%--
  Created by IntelliJ IDEA.
  User: destiny
  Date: 2019/3/7
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/favicon.ico" />
    <title>主页面</title>

    <style>
        .btn{
            color: #fff;
            background-color: #5bc0de;
            border: 1px solid transparent;
            font-size: 20px;
            cursor: pointer;
            border-radius:4px;
        }

        ul>li{
            list-style-type:none;
        }

        ul{
            text-align: left;
        }


    </style>

</head>
<body>

<ul>
    <li style="margin: 10px 0;">
        <a data-url="${pageContext.request.contextPath}/org/list" class="btn">党组织管理</a>
    </li>
    <li>
        <a data-url="${pageContext.request.contextPath}/pat/list" class="btn">党员信息管理</a>

    </li>

</ul>

</body>
</html>
