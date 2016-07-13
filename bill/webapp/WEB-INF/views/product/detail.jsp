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
			<td>产品名称:${product.productName } </td>
			<td>剩余金额：${product.surplusAmount }</td>
			<td>年利率：${product.annualRate }%</td>
			<td>期限：${product.deadline }天</td>
			<td>进度：${product.investSchedule }</td>
			<td>状态：${product.status }</td>
			
	 	</tr>
	</table>
	<form action="/bill/invest" method="post">
				<input type="hidden" name="productId" value="${product.id }">
				<input type="text" name="amount" value="">
				<input type="submit" value="立刻购买">
	</form>
	



</body>
</html>
