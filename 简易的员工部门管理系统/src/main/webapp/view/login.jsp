
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/static/Jquery/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="modal fade" id="register" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">用户注册</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10" id="username">
                            <input type="text" name="username" class="form-control" id="name" required
                                   placeholder="用户名">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10" id="password">
                            <input type="password" name="password" class="form-control" id="email" placeholder="密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="registerUser">注册</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-3">
        <h1>员工信息管理系统</h1>
    </div>
</div>
<form class="form-horizontal" action="/login" method="post">
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">UserName</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="inputEmail3" placeholder="UserName" name="username">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-3">
            <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox">记住密码
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default" >登陆</button>
        </div>
    </div>
</form>
<div class="col-sm-offset-2 col-sm-10">
    <button  class="btn btn-default" id="registerbutton">注册</button>
</div>
${errorMsg}
<script type="text/javascript">
$("#registerbutton").click(function () {
    reset_form(".form-horizontal")
    $("#register").modal({
        backdrop: "static"
    })
})
function reset_form(ele) {
    $(ele)[0].reset();
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}

$("#registerUser").click(function () {
    $.ajax({
        url:"${Context}/insertUser",
        data:$(".form-horizontal").serialize(),
        type:"POST",
        success:function (result) {
            if(result.code==100)
            {
                alert("注册成功")
            }
        }
    })
})
</script>
</body>
</html>
