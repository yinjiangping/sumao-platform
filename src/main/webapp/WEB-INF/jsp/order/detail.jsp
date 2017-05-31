<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title></title>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/2.0.0/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>
<body onload="showData('${paramMaps.orderNo}')">
<script type="text/javascript">
    function showData(orderNo) {
        $.ajax({
            type: 'get',
            url: '${pageContext.request.contextPath}/queryDetail?orderNo=' + orderNo,
            cache: false,
            dataType: 'json',
            success: function (data) {
                var json = eval(data);
                $("#openID").html(json.openID);
                $("#orderAmt").html(json.orderAmt);
                $("#putOrderTime").html(json.putOrderTime);
                $("#state").html(json.state);
                $("#resCode").html(json.resCode);
                $("#resDesc").html(json.resDesc);
                $("#sName").html(json.sendName);
                $("#rName").html(json.receiveName);
                $("#rPhone").html(json.receivePhone);
                $("#rAddress").html(json.receiveAddress);
                $("#showData").html(json.expressInfo);
                var htmlContext = "<br><br><table class='result-tab' width='100%'>"
                var htmlContext = htmlContext + "<tr><td>图片地址</td><td>图片单价</td><td>图片尺寸</td><td>图片数量</td></tr>"
                $.each(json.imagesResList, function (index, item) {
                    htmlContext += "<tr><td><img style='width:70px;height:50px;' src='" + item.img + "' /></td><td>" + item.price + "</td><td>"+item.picSize+"</td><td>"+item.number+"</td></tr>"
                });
                $("#list").html(htmlContext);
            },
            error: function () {
                return;
            }
        });
    }
</script>
<div class="main-wrap">

    <div class="crumb-wrap">
        <div class="crumb-list"><i class="icon-font"></i><pan class="crumb-name">首页</span><span class="crumb-step">&gt;</span><span class="crumb-name">订单管理</span><span
                class="crumb-step">&gt;</span><span>订单详情</span></div>
    </div>
    <div class="result-wrap">
        <div class="result-content">
            <form action="" method="post" id="myform" name="myform"
                  enctype="multipart/form-data">
                <table  class='result-tab' width="100%" border="1" cellspacing="0" cellpadding="0"
                       style="border-collapse:collapse">
                    <tbody>
                    <tr>
                        <td><i class="require-red"></i>订单编号</td>
                        <td><i class="require-red"></i>订单金额</td>
                        <td><i class="require-red"></i>支付时间</td>
                        <td><i class="require-red"></i>订单状态</td>
                        <td><i class="require-red"></i>响应码</td>
                        <td><i class="require-red"></i>响应描述</td>
                        <td><i class="require-red"></i>用户编号</td>
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
                        <td>
                            <span id="resCode"></span>
                        </td>
                        <td>
                            <span id="resDesc"></span>
                        </td>
                        <td>
                            <span id="openID"></span>
                        </td>
                    </tr>
                    <tr>
                        <td><i class="require-red"></i>发件人</td>
                        <td><i class="require-red"></i>收件人</td>
                        <td><i class="require-red"></i>收件人电话</td>
                        <td colspan="4"><i class="require-red"></i>收件人地址</td>
                    </tr>
                    <tr>
                        <td>
                            <span id="sName">1</span>
                        </td>
                        <td>
                            <span id="rName"></span>
                        </td>
                        <td>
                            <span id="rPhone"></span>
                        </td>
                        <td colspan="4">
                            <span id="rAddress"></span>
                        </td>
                    </tr>
                    </tbody>

                </table>
                <div class="result-content" id="list">
                </div>
                <br>
                <span id="showData"></span>
                <H5 align="center"><input class="btn btn4 border-back text-big input-big" onclick="history.go(-1)" value="返回" type="button"></H5>
            </form>
        </div>
    </div>

</div>
</body>
</html>