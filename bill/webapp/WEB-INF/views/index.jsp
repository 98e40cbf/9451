<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/base.jsp"%>
<html>
<head>
<title>Index page</title>
<script>var ctx = "${staticPath}";</script>
</head>
<body>
	注册:<br>
	<form action="${contextPath}/user/regist" method="post">
		username:<input name="username" type="text" /><br/>
		mobile:<input name="mobile" type="text" /><br/>
		password:<input name="password" type="text" /><br/>
		<b:token/>
		<input type="submit" />
	</form>
	实名认证:<br>
	<form action="${contextPath}/user/realname" method="post">
		realName:<input name="realName" type="text" /><br/>
		idCardNo:<input name="idCardNo" type="text" /><br/>
		<b:token/>
		<input type="submit" />
	</form>
	<br/>
	充值:<br/>
	<form action="${contextPath}/user/recharge" method="post">
		<b:token/>
		<input type="submit" />
	</form>
	投资:<br/>
	<form action="${contextPath}/user/buy" method="post">
		<b:token/>
		<input type="text" name="amount"/>
		<input type="submit" />
	</form>
	投资成功:<br/>
	<form action="${contextPath}/user/buy-complete" method="post">
		<b:token/>
		<input type="text" name="txnId">
		<input type="submit" />
	</form>
	投资失败:<br/>
	<form action="${contextPath}/user/buy-fallback" method="post">
		<b:token/>
		<input type="text" name="txnId">
		<input type="submit" />
	</form>
</body>
</html>
