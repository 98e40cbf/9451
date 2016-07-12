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
<h1>支付系统测试</h1>
<table>
    <tr>
        <td>申请开通快捷支付协议:<br>

            <form action="${contextPath}/user/regist" method="post">
                <table>
                    <tr>
                        <td>银行卡号</td><td><input name="username" type="text"/></td>
                    </tr>
                </table>
                username:<br/> mobile:<input
                    name="mobile" type="text"/><br/> password:<input
                    name="password" type="text"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
        <td>确认开通快捷支付协议:<br>

            <form action="${contextPath}/user/login" method="post">
                name:<input name="loginId" type="text"/><br/> password:<input
                    name="password" type="password"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>实名认证: <br>

            <form action="${contextPath}/user/realname" method="post">
                userId:<input name="userId" type="text"/><br/> realName:<input
                    name="realName" type="text"/><br/> idCardNo:<input
                    name="idCardNo" type="text"/><br/>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
        <td>充值: <br/>

            <form action="${contextPath}/user/recharge" method="post">
                <b:token/>
                userId:<input name="userId" type="text"/><br/> amount:<input
                    name="amount" type="text"/><br/> <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>投资: <br/>

            <form action="${contextPath}/user/buy" method="post">
                <b:token/>
                userId:<input name="userId" type="text"/><br/> amount:<input
                    name="amount" type="text"/><br/> <input type="submit"/>
            </form>
        </td>
        <td>投资成功: <br/>

            <form action="${contextPath}/user/buy-complete" method="post">
                <b:token/>
                userId:<input name="userId" type="text"/><br/> origTxnId:<input
                    type="text" name="txnId"><br/> <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>投资失败: <br/>

            <form action="${contextPath}/user/buy-fallback" method="post">
                <b:token/>
                userId:<input name="userId" type="text"/><br/> origTxnId:<input
                    type="text" name="txnId"><br/> <input type="submit"/>
            </form>
        </td>
        <td>&nbsp;</td>
    </tr>
</table>


</body>
</html>
