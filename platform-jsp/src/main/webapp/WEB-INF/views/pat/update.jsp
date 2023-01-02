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
    <title>主页面</title>

    <style>

        td, th {
            border: #dddddd 2px solid;

        }

    </style>

</head>
<body>


<div>

    <form id="save_form">
        <table>
            <tr>
                <td>
                 修改党员信息
                </td>
            </tr>
            <tr>
                <td>党员名称</td>
                <input type="hidden" value="${data.id}" name="id">
                <td>
                    <input type="text" name="username" placeholder="请输入党员名称" value="${data.username}">
                </td>
                <td>党员住址</td>
                <td>
                    <input type="text" name="address" placeholder="请输入党员住址" value="${data.address}">
                </td>
                <td>电话号码</td>
                <td>
                    <input type="text" name="cellphone" placeholder="请输入电话号码" value="${data.cellphone}">
                </td>

            </tr>
            <tr>
                <td>介绍人</td>
                <td>
                    <input type="text" name="liaison" placeholder="请输入介绍人" value="${data.introducer}">
                </td>
                <td>年龄</td>
                <td>
                    <input type="text" name="liaison" placeholder="请输入年龄" value="${data.age}">
                </td>
                <td>组织状态</td>
                <td>
                    <select name="status">

                        <option value="1"
                                <c:if test="${data.status == '1'}">
                                    selected="selected"
                                </c:if>
                        >正常</option>
                        <option value="0"
                                <c:if test="${data.status == '0'}">
                                    selected="selected"
                                </c:if>
                        >禁用</option>

                    </select>
                </td>
            </tr>


            <tr>
                <td colspan="4" style="text-align: right">
                    <button type="button" id="save_org"> 保存</button>
                </td>
            </tr>


        </table>

    </form>


</div>

<script>

    $(function () {

        //
        // $("#add_btn").on("click", function () {
        //     replace_general("org/list", "right_side");
        // });


        $("#save_org").on("click", function () {

            $.ajax({
                url:  "${pageContext.request.contextPath}/pat/update",
                type: 'post',
                dataType: "json",
                cache: false,
                contentType: "application/json;charset=UTF-8",
                // data: $("#save_form").serialize(),
                data: JSON.stringify($('#save_form').serializeObject()),
                timeout: 1000,
                success: function (data) {
                    console.info("success", data);
                    replace_general( "${pageContext.request.contextPath}/pat/list", "right_side");
                },
                error: function (msg) {
                }
            });

        });


    })


</script>


</body>
</html>
