<%--
  Created by IntelliJ IDEA.
  User: destiny
  Date: 2019/3/7
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/favicon.ico"/>
    <title>登录页面</title>


    <style>

        html{
            width: 100%;
            height: 100%;
            overflow: hidden;
            /*font-style: sans-serif;*/
        }

        body {
            width:100%;
            height:100%;
            font-family: 'Open Sans', sans-serif;
            margin:0;
            background-color: #4A374A;
        }

        #login {
            position: absolute;
            top:50%;
            left: 50%;
            margin: -150px 0px 0px -150px;
            width: 300px;
            height: 300px;
        }

        #login h1 {
            color: #fff;
            /*text-shadow: 0 0 10px;*/
            letter-spacing:1px;
            text-align:center;
        }

        h1 {
            font-size: 2em;
            margin: 0.67em 0;
        }

        input {
            width: 278px;
            /*height: 28px;*/
            margin-bottom: 10px;
            outline: none;
            padding: 10px;
            font-size: 18px;
            color: #fff;
            /*text-shadow: 1px 1px 1px;*/
            border-top: 1px solid #312E3D;
            border-left: 1px solid #312E3D;
            border-right: 1px solid #312E3D;
            border-bottom: 1px solid #56536A;
            border-radius: 4px;
            background-color: #2D2D3F;
        }

        .but {
            width: 278px;
            min-height: 20px;
            display: block;
            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }

        body{
            /* https://oscimg.oschina.net/oscnet/up-f1daee76fc104a5271aa552bc61a7a5481f.png */
            background: url(https://oscimg.oschina.net/oscnet/up-f1daee76fc104a5271aa552bc61a7a5481f.png) repeat 100% 100%;;
        }

         
    </style>

</head>
<body>


<div id="login">
    <h1>登录页面
        <c:if test="${not empty message}">
            <div class="alert ${style}" id="alert">
                <button class="close" data-dismiss="alert">
                    <p>&times;</p>
                </button>
                    ${message}
            </div>
        </c:if>
    </h1>
    <form action="${pageContext.request.contextPath}/login/form" method="post">
        <input type="text" placeholder="用户名" name="username">
        <input type="password" placeholder="密码" name="password">
        <button type="submit" class="but">登录</button>
    </form>
</div>



</body>
</html>
