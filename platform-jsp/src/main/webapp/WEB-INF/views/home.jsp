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

        table{
            border-collapse: collapse;
        }
        th, td {
            width: auto;
            height: 40px;
            border:  1px solid black;
            font-size: 15px;
            text-align: center;
            padding: 3px 3px;
        }


        a {
            text-decoration: none;
        }

        input, select {
            width: 200px;
            height: 25px;
            margin: auto;
            outline: none;
            /*padding: 10px;*/
            font-size: 15px;
            color: #333333;
            /*text-shadow: 1px 1px 1px;*/
            border-top: 1px solid #312E3D;
            border-left: 1px solid #312E3D;
            border-right: 1px solid #312E3D;
            border-bottom: 1px solid #56536A;
            border-radius: 4px;
        }
        td{
            text-align: center;
            vertical-align: center;
        }


        #add_btn {
            width: 120px;
            min-height: 15px;
            background-color: #6666CC ;
            border: 1px solid #6666CC ;
            color: #fff;
            padding: 2px 3px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }

        #search_btn, #save_org, #quit_btn {
            width: 50px;
            min-height: 15px;
            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 2px 3px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }

        #p_pre, #p_next {
            width: 60px;
            min-height: 15px;
            background-color: #568dbd;
            border: 1px solid #568dbd;
            color: #fff;
            padding: 2px 3px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }

        #reset_btn{
            width: 50px;
            min-height: 15px;
            background-color: #5bc0de;
            border: 1px solid #5bc0de;
            color: #fff;
            padding: 2px 3px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }

        button[data-del] {
            width: 50px;
            min-height: 15px;
            /*display: block;*/
            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 1px 2px;
            font-size: 15px;
            line-height: normal;
            border-radius: 3px;
            margin: 0;
        }



        button[data-update] {
            width: 50px;
            min-height: 15px;
            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 1px 2px;
            font-size: 15px;
            line-height: normal;
            border-radius: 3px;
            margin: 0;
        }



    </style>

</head>
<body>




<table width="100%" style="height: 100%; width: 100%;vertical-align: center" border="0">
    <tr>
        <td colspan="2" style="background-color:#99ccff;height: 10%">
            <h1>党员信息管理系统
                <a id="quit_btn" type="button"  href="${pageContext.request.contextPath}/logout"  style="text-align: right"  >退出系统</a>
            </h1>

        </td>

    </tr>
    <tr >

        <td style="background-color:#ccffcc;width:100px;vertical-align:top;height: 90%" id="left_menu">
            <jsp:include page="./left.jsp"/>
        </td>

        <td style="background-color:#eeeeee;height:200px;width:400px;vertical-align:top;padding: 10px" id="right_side">

            内容在这里

        </td>
    </tr>

    <tr>
        <td colspan="2" style="background-color:#ffffcc;text-align:center;height: 10%">
            版权所有</td>
    </tr>
</table>






<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.2.3.min.js"></script>
<script src="${pageContext.request.contextPath}/static/app.js"></script>
<script>


    $(function () {
        replace_general("${pageContext.request.contextPath}/org/list", "right_side");

        $("#left_menu a[data-url]").each(function () {

            $(this).on("click", function () {
                var $url = $(this).attr("data-url");
                replace_general($url, "right_side");
            })
        })

        // $("#quit_btn").on("click",function () {
        //
        //     $.get("/logout")
        // })

    })


</script>




</body>
</html>
