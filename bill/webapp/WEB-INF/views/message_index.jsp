<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/base.jsp" %>
<html>
<head>
    <title>Index page</title>
    <script>
        var ctx = "${staticPath}";
    </script>
</head>
<body>
<table>
    <tr>
        <td>注册:<br>

            <form action="${contextPath}/message/regist" method="post">
                username:<input name="username" type="text"/><br/> mobile:<input
                    name="mobile" type="text"/><br/> password:<input
                    name="password" type="text"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>

</table>


</body>
</html>
