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

                <input type="hidden" id="current" name="current" value="1">
                <input type="hidden" id="pageSize" name="pageSize" value="10" >

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

        function fil_table(datas) {
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
            $("#body_content").html("").html(html);
        }


        function page() {
            $("#body_content").html("");
            var params = $("#search_form").serialize();
            console.info(params)

            $.ajax({
                url: "${pageContext.request.contextPath}/org/page",
                type: 'get',
                dataType: "json",
                cache: false,
                data: $("#search_form").serialize(),
                success: function (result) {

                    var datas = result.data.list;
                    var total = result.data.total;
                    var current = result.data.current;
                    var pages = result.data.pages;
                    var pageSize = result.data.pageSize;

                    $("#current").val(current);
                    $("#pageSize").val(pageSize);

                    fil_table(datas);


                    $("#pagination_list").pagination({
                        num_edge_entries: 1, //边缘页数
                        num_display_entries: pages, //主体页数
                        items_per_page: pageSize, //每页显示1项
                        prev_text: "上一页",
                        next_text: "下一页",
                        pageCount: pages,
                        totalData: total,
                        current_page: current,
                        callback: function () {


                            $.ajax({
                                url: "${pageContext.request.contextPath}/org/page",
                                type: 'get',
                                dataType: "json",
                                cache: false,
                                data: $("#save_form").serialize(),
                                success: function (result) {
                                    var datas = result.data.list;
                                    var total = result.data.total;
                                    var current = result.data.current;
                                    var pages = result.data.pages;
                                    fil_table(datas);

                                }


                            });

                            
                        }
                    });


                }


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
