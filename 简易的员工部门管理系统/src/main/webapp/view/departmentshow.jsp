<%--
  Created by IntelliJ IDEA.
  User: 87366
  Date: 2018/9/3
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/static/Jquery/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <%
        pageContext.setAttribute("Context", request.getContextPath());
    %>
</head>
<body>
<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>部门管理系统</h1>
        </div>
    </div>
    <!--显示表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped" id="departmenttable">
                <thead>
                <th>部门编号</th>
                <th>部门名称</th>
                <th>操作</th>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url: "${Context}/DeptsJson",
            type: "Get",
            success: function (result) {
                <!-- 解析Json数据-->
                departmentshow(result)
            }
        })
    })
    var departmentshow = function (result) {
        $("#departmenttable tbody").empty();
        var list =result.map.departments;
        $.each(list, function (index, item) {
            var departmentId = $("<td></td>").append(item.depId);
            var departmentname=$("<td></td>").append(item.depName);
            var addbutton = $("<button id='updateemp'></button>").addClass("btn btn-primary").append("修改");
            var deletebutton = $("<button id='deleteemp'></button>").addClass("btn btn-danger").append("删除");
            $("<tr></tr>")
                .append(departmentId)
                .append(departmentname)
                .append(addbutton)
                .append(deletebutton)
                .appendTo("#departmenttable tbody")
        })
    }
</script>
</body>
</html>
