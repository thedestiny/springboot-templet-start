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

                <input type="hidden" id="pre" >
                <input type="hidden" id="next" >

            </tr>

            <tr>
                <td colspan="4" style="text-align: right">
                    <button type="button" id="search_btn"> 查询</button>
                    <button type="reset" id="reset_btn"> 重置</button>
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
                   <table>
                       <tr>
                           <td></td>
                           <td >
                               <button id="p_pre" >上一页</button>
                           </td>
                           <td >
                               <button id="p_next" >下一页</button>
                           </td>
                           <td id="p_size">
                               数据总页数
                           </td>
                           <td id="p_total">
                               数据总条数
                           </td>

                       </tr>

                   </table>
             </div>



     </div>



</div>


<script>

    $(function () {


        $("#body_content").on("click","button[data-del]",function () {
             var id = $(this).attr("data-del");
             $.get("${pageContext.request.contextPath}/org/del?id="+ id,function () {
                 alert("删除成功!");
                 load_data();
             })

        });
        $("#body_content").on("click","button[data-update]",function () {
            var id = $(this).attr("data-update");
            replace_general( "${pageContext.request.contextPath}/org/update?id=" + id, "right_side");
        });


        $("#add_btn").on("click",function () {
            replace_general("${pageContext.request.contextPath}/org/add", "right_side");
        });


        $("#search_btn").on("click",function () {
            $("#body_content").html("");
            load_data();
        });


        $("#p_pre").on("click",function () {
            $("#body_content").html("");
            var pre = $("#pre").val();
            console.info("pre " , pre);
            $("#current").val(pre);
            load_data();
        });

        $("#p_next").on("click",function () {
            $("#body_content").html("");
            var nxt = $("#next").val();
            console.info("next ", nxt);
            $("#current").val(nxt);
            load_data();
        });


        load_data();

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


        function load_data() {

            $.ajax({
                url: "${pageContext.request.contextPath}/org/page",
                type: 'get',
                dataType: "json",
                cache: false,
                data: $("#search_form").serialize(),
                timeout: 1000,
                success: function (result) {
                    var datas = result.data.list;
                    var total = result.data.total;
                    var current = result.data.current;
                    var pages = result.data.pages;
                    var pageSize = result.data.pageSize;
                    var pre = result.data.pre;
                    var next = result.data.next;

                    // 总页数
                    var ph = current + "/" + pages + " 当前页/总页数,每页 " + pageSize + " 条";
                    $("#p_size").html(ph);
                    $("#p_total").html("一共" + total + " 条数据");

                    $("#pre").val(pre);
                    $("#next").val(next);

                    fil_table(datas)


                },
                error: function (msg) {
                }
            });


        }



    })


</script>


</body>
</html>
