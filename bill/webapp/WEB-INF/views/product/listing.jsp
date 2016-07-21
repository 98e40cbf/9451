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
    <c:forEach var="product" items="${productList.data}">

        <tr>
            <td>产品名称:${product.productName } </td>
            <td>剩余金额：${product.surplusAmount }</td>
            <td>年利率：${product.annualRate }%</td>
            <td>期限：${product.deadline }天</td>
            <td>进度：${product.investSchedule }</td>
            <td>状态：${product.status }</td>

            <td><a href="detail/${product.id }">购 买</a></td>
        </tr>
    </c:forEach>
</table>

<div class="pages">
    <div class="page">
        <c:set var="indexUrl" value="bill/product/list" scope="page"/>

        第<i class="pg">${pageNum}</i>页/共<i class="cal"><fmt:formatNumber value="${totalPage}"/></i>页
        <c:if test="${pageNum <= 1}">
            <a href="javascript:;">首页</a>
            <a href="javascript:;">上一页</a>
        </c:if>
        <c:if test="${pageNum > 1}">
            <a href="${indexUrl}">首页</a>
        </c:if>
        <c:if test="${pageNum == 2}">
            <a href="${indexUrl}">上一页</a>
        </c:if>
        <c:if test="${pageNum > 2}">
            <a href="${indexUrl}/${pageNum - 1}">上一页</a>
        </c:if>
        <c:set var="maxPage" value="5" scope="page"/>
        <c:set var="offset" value="${(maxPage + 2 - maxPage % 2) / 2}" scope="page"/>
        <c:choose>
            <c:when test="${totalPage < maxPage}">
                <c:set var="begin" value="1" scope="page"/>
                <c:set var="end" value="${totalPage}" scope="page"/>
            </c:when>
            <c:when test="${pageNum < offset}">
                <c:set var="begin" value="1" scope="page"/>
                <c:set var="end" value="${totalPage < maxPage ? totalPage : maxPage}" scope="page"/>
            </c:when>
            <c:when test="${pageNum + maxPage - offset > totalPage}">
                <c:set var="begin" value="${totalPage - maxPage + 1}" scope="page"/>
                <c:set var="end" value="${totalPage}" scope="page"/>
            </c:when>
            <c:otherwise>
                <c:set var="begin" value="${pageNum - offset + 1}" scope="page"/>
                <c:set var="end" value="${pageNum + maxPage - offset}" scope="page"/>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="${begin}" end="${end}" varStatus="status">
            <c:if test="${status.index == pageNum}">
                <a href="javascript:;" class="cur">${status.index}</a>
            </c:if>
            <c:if test="${status.index != pageNum}">
                <a href="${indexUrl}<c:if test="${status.index > 1}">/${status.index}</c:if>">${status.index}</a>
            </c:if>
        </c:forEach>
        <c:if test="${pageNum >= totalPage}">
            <a href="javascript:;">下一页</a>
            <a href="javascript:;">末页</a>
        </c:if>
        <c:if test="${pageNum < totalPage}">
            <a href="${indexUrl}/${pageNum + 1}">下一页</a>
            <a href="${indexUrl}/${totalPage}">末页</a>
        </c:if>
    </div>
    <input type="hidden" id="pageCal" value="" data-new-url="${contextPath}" data-old-url="${contextPath }"
           data-img=".." total-row="${productList.totalRow}"/>
</div>


</body>
</html>
