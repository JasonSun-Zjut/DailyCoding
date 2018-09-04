<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
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
<!--员工添加模态框-->
<div class="modal fade" id="empaddmodel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增雇员信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">员工姓名</label>
                        <div class="col-sm-10" id="namediv">
                            <input type="text" name="empName" class="form-control" id="empName" required
                                   placeholder="员工姓名">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="MaleRadio" value="M" checked="true"> 男 </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="FemaleRadio" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10" id="emaildiv">
                            <input type="email" name="email" class="form-control" id="email" placeholder="邮箱">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="departments">
                                <!--ajax请求 -->
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="empsave">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
            </div>
        </div>
    </div>
</div>
<!--员工修改模态框-->
<div class="modal fade" id="empupdatemodel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="">修改雇员信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">员工姓名</label>
                        <div class="col-sm-10" id="nameupdatediv">
                            <input type="text" name="empName" class="form-control" id="empupdateName" required
                                   placeholder="员工姓名">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender"  value="M" checked="true"> 男 </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender"  value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10" id="emailupdatediv">
                            <input type="email" name="email" class="form-control" id="emailupdate" placeholder="邮箱">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">部门</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="departmentsupdate">
                                <!--ajax请求 -->
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="empupdate">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <!--标题-->
    <div class="row">
        <div class="col-md-12">
            <h1>员工信息管理系统</h1>
        </div>
    </div>
    <!--按钮-->
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <button type="button" class="btn btn-primary" id="emp_add">新增</button>
            <button type="button" class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--显示表格数据-->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped" id="emptable">
                <thead>
                <th>ID</th>
                <th>EmpName</th>
                <th>Gender</th>
                <th>DeptName</th>
                <th>操作</th>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!--显示分页信息-->
    <div class="row">
        <div class="col-md-6" id="page_info">
            当前第:<span id="pageNum"></span>页，总共<span id="pages"></span>页，共有<span id="total"></span>条记录
        </div>
        <div class="col-md-6" id="pagenav">

        </div>
    </div>
</div>

<!--EmployeeAdd-->
<script type="text/javascript">
    $(function () {
            to_page(1)
        }
    );
    <!--*********ajax请求********* -->
    var to_page = function (pageNum) {
        $.ajax({
            url: "${Context}/empsJson",
            data: "pageNum=" + pageNum,
            type: "Get",
            success: function (result) {
                <!-- 解析Json数据-->
                empshow(result)
                datashow(result)
                buildpagenav(result)
            }
        })
    }
    <!--*********展示员工信息********* -->
    var empshow = function (result) {
        $("#emptable tbody").empty();
        var list = result.map.pageInfo.list;
        $.each(list, function (index, item) {
            var empIdtd = $("<td></td>").append(item.empId);
            var empNametd = $("<td></td>").append(item.empName);
            var gendertd = $("<td></td>").append(item.gender);
            var emailtd = $("<td></td>").append(item.email);
            var departmenttd = $("<td></td>").append(item.department.depName);
            var addbutton = $("<button id='updateemp'></button>").addClass("btn btn-primary").append("修改");
            var deletebutton = $("<button id='deleteemp'></button>").addClass("btn btn-danger").append("删除");
            $("<tr></tr>").append(empIdtd)
                .append(empNametd)
                .append(gendertd)
                .append(emailtd)
                .append(departmenttd)
                .append(addbutton)
                .append(deletebutton)
                .appendTo("#emptable tbody")
        })
    }
    <!-- *********记录数*********-->
    var datashow = function (result) {
        $("#pageNum").empty()
        $("#pages").empty()
        $("#total").empty()
        var pageNum = result.map.pageInfo.pageNum;
        var pages = result.map.pageInfo.pages;
        var total = result.map.pageInfo.total;
        $("#pageNum").append(pageNum);
        $("#pages").append(pages);
        $("#total").append(total);
    }

    <!--*********分页表*********-->
    var buildpagenav = function (result) {
        $("#pagenav").empty()
        var ul = $("<ul></ul>").addClass("pagination");
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        firstPageLi.click(function () {
            to_page(1)
        })
        if (!result.map.pageInfo.hasPreviousPage) {
            firstPageLi.addClass("disabled");
        }
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
        lastPageLi.click(function () {
            to_page(result.map.pageInfo.pages)
        })
        if (!result.map.pageInfo.hasNextPage) {
            lastPageLi.addClass("disabled");
        }
        var perPageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
        perPageLi.click(function () {
            to_page(result.map.pageInfo.prePage)
        })

        nextPageLi.click(function () {
            to_page(result.map.pageInfo.nextPage)
        })
        ul.append(firstPageLi)
        if (result.map.pageInfo.hasPreviousPage) {
            ul.append(perPageLi);
        }
        $.each(result.map.pageInfo.navigatepageNums, function (index, navinum) {
            var numLi = $("<li></li>").append($("<a></a>").append(navinum));
            if (navinum == result.map.pageInfo.pageNum) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(navinum)
            })
            ul.append(numLi);
        });
        if (result.map.pageInfo.hasNextPage) {
            ul.append(nextPageLi);
        }
        ul.append(lastPageLi);
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#pagenav");
    }


       <!--*********获取部门信息********* -->
    function getDepts(ele) {
        $.ajax({
            url: "${Context}/DeptsJson",
            type: "Get",
            success: function (result) {
                $(ele).empty();
                $.each(result.map.departments, function (index, department) {
                    var departopt = $("<option></option>").append(department.deptName).attr("value", this.deptId);
                    $(ele).append(departopt)
                })
            }
        })
    }
         <!--*********校验********* -->
    function validate_form() {
        var empName = $("#empName").val();
        var email = $("#email").val();
        var regName = /(^[A-Za-z0-9]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!regName.test(empName)) {
            show_validate_msg("#empName", "error", "名字必须是2-5个中文或者6-16位英文数字组合")
            return false
        }
        ;
        show_validate_msg("#empName", "success", "")
        if (!regEmail.test(email)) {
            show_validate_msg("#email", "error", "邮箱格式输入错误")
            return false;
        }
        show_validate_msg("#email", "success", "")
        return true
    }
       <!--*********员工名称校验*********-->
    function empNameCheck() {
        $.ajax({
            url: "${Context}/empCheck",
            data: "empName=" + $("#empName").val(),
            type: "POST",
            success: function (result) {
                if (result.code == 100) {
                    show_validate_msg("#empName", "success", "员工用户名可用")
                    return true
                } else if (result.code == 200) {
                    show_validate_msg("#empName", "error", "员工用户名已存在")
                    return false
                }
            }
        })
    }

    function show_validate_msg(ele, status, msg) {
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if ("success" == status) {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text("");
        } else if ("error" == status) {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }
    <!--擦出内容-->
    function reset_form(ele) {
        $(ele)[0].reset();
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");
    }
     <!--*********新增员工*********-->
    $("#emp_add").click(function () {
        reset_form(".form-horizontal")
        $("#empaddmodel").modal({
            backdrop: "static"
        })
        getDepts("#departments")

    })

    $("#empsave").click(function () {
        /*if (!validate_form()) {
            return false
        }*/
        $.ajax({
            url: "${Context}/emp",
            data: $(".form-horizontal").serialize(),
            type: "POST",
            success: function (result) {
                if(result.code==100)
                {
                $('#empaddmodel').modal('hide')
                to_page(1)}
                else if(result.code==200)
                {
                    if(undefined!=result.map.errorMap.email)
                    {
                        show_validate_msg("#email", "error", "邮箱格式输入错误")
                    }
                    if(undefined!=result.map.errorMap.empName)
                    {
                        show_validate_msg("#empName", "error", "名字必须是2-5个中文或者6-16位英文数字组合")
                    }
                }
            }
        })
    })

    $("#empName").change(function () {
        empNameCheck()
    })
</script>
<script type="text/javascript">
$(document).on("click","#updateemp",function () {
    reset_form(".form-horizontal")
    $("#empupdatemodel").modal({
        backdrop: "static"
    })
    getDepts("#departmentsupdate")
})
</script>
</body>
</html>
