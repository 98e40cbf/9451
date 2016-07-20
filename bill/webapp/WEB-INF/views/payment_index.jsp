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

            <form action="${contextPath}/payment/card/apply/agreement" method="post">
                <table>
                    <tr>
                        <td>银行卡号</td>
                        <td><input name="bankCardNo" type="text" value="6227001376710050739"/></td>
                    </tr>
                    <tr>
                        <td>身份证</td>
                        <td><input name="idCardNo" type="text" value="430424198910193913"/></td>
                    </tr>
                    <tr>
                        <td>真实</td>
                        <td><input name="realName" type="text" value="罗淳雅"/></td>
                    </tr>
                    <tr>
                        <td>预留手机号</td>
                        <td><input name="mobile" type="text" value="18588220893"/></td>
                    </tr>
                    <tr>
                        <td>来源</td>
                        <td><input name="origin" type="text" value="PC"/></td>
                    </tr>
                </table>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>确认开通快捷支付协议:<br>

            <form action="${contextPath}/payment/card/confirm/agreement" method="post">
                <table>
                    <tr>
                        <td>验证码</td>
                        <td><input name="securityCode" type="text" value=""/></td>
                    </tr>
                    <tr>
                        <td>来源</td>
                        <td><input name="origin" type="text" value="PC"/></td>
                    </tr>
                </table>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>申请快捷支付:<br>

            <form action="${contextPath}/payment/card/apply/quick/pay" method="post">
                <table>
                    <tr>
                        <td>支付金额</td>
                        <td><input name="amount" type="text" value="1000.00"/></td>
                    </tr>
                    <tr>
                        <td>来源</td>
                        <td><input name="origin" type="text" value="PC"/></td>
                    </tr>
                </table>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>
    <tr>
        <td>确认快捷支付:<br>

            <form action="${contextPath}/payment/card/confirm/quick/pay" method="post">
                <table>
                    <tr>
                        <td>验证码</td>
                        <td><input name="securityCode" type="text" value=""/></td>
                    </tr>
                    <tr>
                        <td>来源</td>
                        <td><input name="origin" type="text" value="PC"/></td>
                    </tr>
                </table>
                <b:token/>
                <input type="submit"/>
            </form>
        </td>
    </tr>
</table>


</body>
</html>
