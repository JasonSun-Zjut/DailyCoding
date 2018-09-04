<%--
  Created by IntelliJ IDEA.
  User: 87366
  Date: 2018/9/1
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/static/Jquery/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="row">
    <div class="col-md-12">
        <h1>你不具备此权力！</h1>
    </div>
</div>
<script>
    window.onload = function(){
        setTimeout("history.back()", 5000);
    }
</script>
</body>
</html>
