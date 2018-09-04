<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <%
    pageContext.setAttribute("Context",request.getContextPath());
    %>
    <title>Title</title>
    <script type="text/javascript" src="static/Jquery/jquery-3.3.1.js"></script>
    <link rel="stylesheet" href="static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
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
            <button type="button" class="btn btn-primary">新增</button>
            <button type="button" class="btn btn-danger">删除</button>
        </div>
    </div>
    <!--显示表格数据-->
    <div class="row">
 <div class="col-md-12">
     <table class="table-hover">
         <tr>
             <th>ID</th>
             <th>EmpName</th>
             <th>Gender</th>
             <th>DeptName</th>
             <th>操作</th>
         </tr>
         <c:forEach items="${PageInfo.list}" var="emp">
             <tr>
                 <th>${emp.empId }</th>
                 <th>${emp.empName }</th>
                 <th>${emp.gender=="M"?"男":"女" }</th>
                 <th>${emp.email }</th>
                 <th>${emp.department.deptName }</th>
                 <th>
                     <button type="button" class="btn btn-primary btn-sm">
                         修改 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                     </button>
                     <button type="button" class="btn btn-danger btn-sm">
                         删除 <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                     </button>
                 </th>
             </tr>
         </c:forEach>
     </table>
 </div>

    </div>
    <!--显示分页信息-->
    <div class="row">
        <div class="col-md-6">
            当前第：${PageInfo.pageNum}页，总共${PageInfo.pages}页，共有${PageInfo.total}条记录
        </div>
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${!PageInfo.isFirstPage}">
                        <li><a href="${Context}/emps?pageNum=1">首页</a></li>
                    </c:if>
                    <!--前一页 -->
                    <c:if test="${PageInfo.hasPreviousPage }">
                        <li><a href="${Context }/emps?pageNum=${PageInfo.pageNum-1}" aria-label="Previous"> <span
                                aria-hidden="true">&laquo;</span>
                        </a></li>
                    </c:if>

                    <c:forEach items="${PageInfo.navigatepageNums }" var="currentPageNum">
                        <!-- 是当前页面则高亮显示-->
                        <c:if test="${currentPageNum == PageInfo.pageNum }">
                            <li class="active"><a href="#">${currentPageNum}</a></li>
                        </c:if>
                        <c:if test="${currentPageNum != PageInfo.pageNum }">
                            <li><a href="${Context}/emps?pageNum=${currentPageNum}">${currentPageNum}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${PageInfo.hasNextPage }">
                        <li><a href="${Context }/emps?pageNum=${PageInfo.pageNum+1}" aria-label="Next"> <span
                                aria-hidden="true">&raquo;</span>
                        </a></li>
                    </c:if>
                    <li><a href="${Context}/emps?pageNum=${PageInfo.pages}">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
