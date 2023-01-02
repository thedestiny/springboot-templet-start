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

        td, th{
            border: #dddddd 2px solid ;

        }

    </style>

</head>
<body>


<div>

    <form id="search_form">
        <table>
            <tr>
                <td>组织名称</td>
                <td>
                    <input type="text" name="username" placeholder="请输入组织名称">
                </td>
                <td>组织地址</td>
                <td>
                    <input type="text" name="address" placeholder="请输入组织地址">
                </td>
            </tr>

            <tr>
                <td colspan="4" style="text-align: right">
                    <button type="button" id="search_btn"> 搜索</button>
                </td>
            </tr>

            <tr>
                <td>
                    <button type="button" id="add_btn">新增党组织</button>
                </td>
            </tr>
        </table>

    </form>

     <div id="content">
         <table>
              <thead>
              <tr>
                 <th>主键</th>
                 <th>组织名称</th>
                 <th>组织状态</th>
                 <th>组织地址</th>
                 <th>联系电话</th>
                 <th>联系人</th>
                 <th>创建时间</th>
                 <th>修改时间</th>
                 <th>操作</th>
              </tr>
              </thead>
             <tbody id="body_content">
             <tr>
                 <td>d</td>
                 <td>s</td>
                 <td>s</td>
                 <td>s</td>
                 <td>s</td>
                 <td>s</td>
                 <td>s</td>
                 <td>s</td>
                 <td>
                     <button type="button" style="background-color: red" data-del="2">删除</button>
                     <button type="button" style="background-color: #5bc0de" data-update="3">修改</button>
                 </td>
             </tr>


             </tbody>

         </table>

             <div id="pagination_list">

             </div>



     </div>



</div>

<script type="text/x-handlebars-template" id="tpl_org">
    　{{#each data}}
    <%--<tr>--%>
        <%--<td>{{code}}</td>--%>
        <%--&lt;%&ndash;<td>{{name}}</td>&ndash;%&gt;--%>
        <%--<td>{{priceChange}}</td>--%>
        <%--&lt;%&ndash;  <td>{{changePercent}}</td>--%>
          <%--<td>{{open}}</td>--%>
          <%--<td>{{high}}</td>--%>
          <%--<td>{{low}}</td>--%>
          <%--<td>{{close}}</td>--%>
          <%--<td>{{volume}}</td>--%>
          <%--<td>{{amount}}</td>&ndash;%&gt;--%>
        <%--<td>{{prettifyDate timeNode "YYYY-MM-DD"}}</td>--%>
        <%--<td>--%>
            <%--<button class="btn btn-primary btn-sm" data-focus="{{code}}">关注</button>--%>
        <%--</td>--%>

    <tr>
        <td>{{id}}</td>
        <td>{{username}}</td>
        <td>{{statusChg status}}</td>
        <td>{{address}}</td>
        <td>{{address}}</td>
        <td>{{address}}</td>
        <td>{{prettifyDate createTime "YYYY-MM-DD"}}</td>
        <td>{{prettifyDate updateTime "YYYY-MM-DD"}}</td>
        <td>
            <button type="button" style="background-color: red" data-del="{{id}}">删除</button>
            <button type="button" style="background-color: #5bc0de" data-update="{{id}}">修改</button>
        </td>
    </tr>
    {{/each}}

</script>

<script>

    $(function () {



        $("#add_btn").on("click",function () {
            replace_general("org/add", "right_side");
        });

        // load_data();

        $("#search_btn").on("click",function () {
            // load_data();
            console.info("dddd");
            $("#body_content").html("");
            page();
        });

        page();

        function page() {

            $("#pagination_list").page({
                firstBtnText: '首页',
                lastBtnText: '尾页',
                prevBtnText: '上一页',
                nextBtnText: '下一页',
                showInfo: true,
                showJump: true,
                jumpBtnText: '跳转',
                showPageSizes: true,
                remote: {
                    url: '${pageContext.request.contextPath}/org/page',
                    params: $("#save_form").serialize(),
                    success: function (result, pageIndex) {
                        var datas = result.data.list;
                        var html = "";
                        for(var i in datas){
                            var node = datas[i];
                            var st = "正常";
                            if (node.status == '0'){
                                st = "禁用";
                            }
                            var fmt = "yyyy-MM-dd hh:mm:ss";
                            var crt = new Date(node.createTime).format(fmt);
                            var upt = new Date(node.updateTime).format(fmt);

                            var tpl = "<tr>\n" +
                                "                 <td>"+ node.id +"</td>\n" +
                                "                 <td> " + node.username +"</td>\n" +
                                "                 <td>" + st +"</td>\n" +
                                "                 <td>" + node.address +"</td>\n" +
                                "                 <td> " + node.cellphone +"</td>\n" +
                                "                 <td> "  + node.liaison +"</td>\n" +
                                "                 <td> " + crt +"</td>\n" +
                                "                 <td> " + upt +" </td>\n" +
                                "                 <td>\n" +
                                "                     <button type=\"button\" style=\"background-color: red\" data-del=\"" + node.id +"\">删除</button>\n" +
                                "                     <button type=\"button\" style=\"background-color: #5bc0de\" data-update=\" " + node.id +"\">修改</button>\n" +
                                "                 </td>\n" +
                                "             </tr>";

                            html += tpl;

                        }
                        $("#body_content").html("").html(html)

                        //回调函数
                        //result 为 请求返回的数据，呈现数据
                    },
                    pageIndexName: 'data.current',     //请求参数，当前页数，索引从0开始
                    pageSizeName: 'data.pageSize',       //请求参数，每页数量
                    totalName: 'data.total'              //指定返回数据的总数据量的字段名
                },
                infoFormat: '{start} ~ {end}条，共{total}条'
            });

        }


        function load_data() {

            $.ajax({
                url: "org/page",
                type: 'get',
                dataType: "json",
                cache: false,
                data: $("#save_form").serialize(),
                timeout: 1000,
                success: function (result) {
                    var datas = result.data.list;



                },
                error: function (msg) {
                }
            });


        }



    })


</script>


</body>
</html>
