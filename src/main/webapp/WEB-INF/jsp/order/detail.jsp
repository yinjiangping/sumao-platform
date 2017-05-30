<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>

<body onload="showData('${paramMaps.orderNo}')">
<script type="text/javascript">
    function showData(orderNo) {
        $.ajax({
            type: 'get',
            url: '${pageContext.request.contextPath}/queryDetail?orderNo='+orderNo,
            cache: false,
            dataType: 'json',
            success: function (data) {
                var json = eval(data);
               $("#orderAmt").html(json.orderAmt);
                $("#putOrderTime").html(json.putOrderTime);
                $("#state").html(json.state);
            },
            error: function () {
                return;
            }
        });
    }
</script>
<div class="main-wrap">

    <div class="crumb-wrap">
        <div class="crumb-list"><i class="icon-font"></i><a href="/jscss/admin/design/">首页</a><span class="crumb-step">&gt;</span><a
                class="crumb-name" href="/jscss/admin/design/">订单管理</a><span
                class="crumb-step">&gt;</span><span>订单详情</span></div>
    </div>
    <div class="result-wrap">
        <div class="result-content">
            <form action="" method="post" id="myform" name="myform"
                  enctype="multipart/form-data">
                <table class="insert-tab" width="100%"  border="1" cellspacing="0" cellpadding="0"
                       style="border-collapse:collapse">
                    <tbody>
                    <tr>
                        <td><i class="require-red"></i>订单编号</td>
                        <td><i class="require-red"></i>订单金额</td>
                        <td><i class="require-red"></i>支付时间</td>
                        <td><i class="require-red"></i>订单状态</td>
                    </tr>
                    <tr>
                        <td>
                            <span id="orderNo">${paramMaps.orderNo}</span>
                        </td>
                        <td>
                            <span id="orderAmt"></span>
                        </td>
                        <td>
                            <span id="putOrderTime"></span>
                        </td>
                        <td>
                            <span id="state"></span>
                        </td>
                    </tr>
                    </tbody>

                </table>
                <H5 align="center"><input class="btn btn6" onclick="history.go(-1)" value="返回" type="button"></H5>
            </form>
        </div>
    </div>

</div>
</body>
</html>