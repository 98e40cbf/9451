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
        <td>文本转语音通知:<br>
            <form action="${contextPath}/message/voice" method="post">
                mobile:<input name="mobile" type="text"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>

    <tr>
        <td>普通文本短信:<br>
            <form action="${contextPath}/message/normal" method="post">
                 mobile:<input name="mobile" type="text"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>



</table>


</body>
</html>
