<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/base.jsp"%>
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
				<form action="${contextPath}/user/regist" method="post">
					username:<input name="username" type="text" /><br /> mobile:<input
						name="mobile" type="text" /><br /> password:<input
						name="password" type="text" /><br />
					<b:token />
					<input type="submit" />
				</form>
			</td>
			<td>登陆:<br>
				<form action="${contextPath}/user/login" method="post">
					name:<input name="loginId" type="text" /><br /> password:<input
						name="password" type="password" /><br />
					<b:token />
					<input type="submit" />
				</form>
			</td>
		</tr>
		<tr>
			<td>实名认证: <br>
				<form action="${contextPath}/user/realname" method="post">
					userId:<input name="userId" type="text" /><br /> realName:<input
						name="realName" type="text" /><br /> idCardNo:<input
						name="idCardNo" type="text" /><br />
					<b:token />
					<input type="submit" />
				</form>
			</td>
			<td>充值: <br />
				<form action="${contextPath}/user/recharge" method="post">
					<b:token />
					userId:<input name="userId" type="text" /><br /> amount:<input
						name="amount" type="text" /><br /> <input type="submit" />
				</form>
			</td>
		</tr>
		<tr>
			<td>投资: <br />
				<form action="${contextPath}/user/buy" method="post">
					<b:token />
					userId:<input name="userId" type="text" /><br /> amount:<input
						name="amount" type="text" /><br /> <input type="submit" />
				</form>
			</td>
			<td>投资成功: <br />
				<form action="${contextPath}/user/buy-complete" method="post">
					<b:token />
					userId:<input name="userId" type="text" /><br /> origTxnId:<input
						type="text" name="txnId"><br /> <input type="submit" />
				</form>
			</td>
		</tr>
		<tr>
			<td>投资失败: <br />
				<form action="${contextPath}/user/buy-fallback" method="post">
					<b:token />
					userId:<input name="userId" type="text" /><br /> origTxnId:<input
						type="text" name="txnId"><br /> <input type="submit" />
				</form>
			</td>
			<td>更改密码:<br>
				<form action="${contextPath}/user/update-password" method="post">
					userId:<input name="userId" type="text" /><br /> oldPassword:<input
						name="oldPassword" type="password" /><br /> newPassword:<input
						name="newPassword" type="password" /><br />
					<b:token />
					<input type="submit" />
				</form>
			</td>
		</tr>
		<tr>
			<td>资金记录: <br />
				<form action="${contextPath}/user/list" method="get">
					userId:<input name="userId" type="text" /><br /> <input
						type="submit" />
				</form>
			</td>
			<td>更改手机号:<br>
				<form action="${contextPath}/user/modify-mobile" method="post">
					userId:<input name="userId" type="text" /><br /> oldMobile:<input
						name="oldMobile" type="text" /><br /> newMobile:<input
						name="newMobile" type="text" /><br />
					<b:token />
					<input type="submit" />
				</form></td>
		</tr>
		<tr>
			<td>用户基本信息: <br />
				<form action="${contextPath}/user/get" method="get">
					userId:<input name="userId" type="text" /><br /> <input
						type="submit" />
				</form>
			</td>
			<td>用户扩展信息: <br />
				<form action="${contextPath}/user/extra" method="get">
					userId:<input name="userId" type="text" /><br /> <input
						type="submit" />
				</form>
			</td>
		</tr>
		<tr>
			<td>退出: <br />
				<form action="${contextPath}/user/logout" method="get">
					<input type="submit" />
				</form>
			</td>
			<td>&nbsp;
		</tr>
	</table>



</body>
</html>
