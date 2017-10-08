<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title></title>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/2.0.0/jquery.js"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>

<body>
<script type="text/javascript">
    function submitFrom() {
        var orderNo = $("#orderNo").val();
        if (orderNo == "" || orderNo == undefined || orderNo == null) {
            alert("请输入订单编号");
            return;
        }

        var expressName = $("#expressName option:selected").val();
        if (expressName == "" || expressName == undefined || expressName == null) {
            alert("请输入快递公司名称");
            return;
        }

        var expressNo = $("#expressNo").val();
        if (expressNo == "" || expressNo == undefined || expressNo == null) {
            alert("请输入运单号");
            return;
        }

        var requestParams = "?orderNo=" + orderNo +"&cType=fh" +"&processKey=" + encodeURI(expressName) + "&processValue=" + encodeURI(expressNo);
        htmlobj = $.ajax({url: $("#myform").attr("action") + requestParams, async: false});
        if (htmlobj.responseJSON == true) {
            alert("更新成功");
        }else{
            alert("更新失败");
        }

    }
</script>
<div class="main-wrap">

    <div class="crumb-wrap">
        <div class="crumb-list"><i class="icon-font"></i>首页<span class="crumb-step">&gt;</span>订单管理<span
                class="crumb-step">&gt;</span><span>更新订单</span></div>
    </div>
    <div class="result-wrap">
        <div class="result-content">
            <form action="${pageContext.request.contextPath}/orderDelivery" method="post" id="myform" name="myform"
                  enctype="multipart/form-data">
                <table class="insert-tab" width="100%">
                    <tbody>
                    <tr>
                        <td><i class="require-red"></i>订单编号：</td>
                        <td>
                            <input class="common-text required" id="orderNo" disabled="disabled" name="orderNo"
                                   size="50"
                                   value="${paramMaps.orderNo}" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td><i class="require-red"></i>快递公司：</td>
                        <td>
                            <select id="expressName" name="expressName"  style="width:332px;">
                                <option value="申通快递">申通快递</option>
                                <option value="圆通快递">圆通快递</option>
                                <option value="中通快递">中通快递</option>
                                <option value="百世快递">百世快递</option>
                                <option value="韵达快递">韵达快递</option>
                                <option value="天天快递">天天快递</option>
                                <option value="中国邮政">中国邮政</option>
                                <option value="EMS专递">EMS专递</option>
                                <option value="其它快递">其它快递</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><i class="require-red"></i>运单号：</td>
                        <td>
                            <input class="common-text required" id="expressNo" name="expressNo" size="50"
                                   value="${paramMaps.expressNo}" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="btn btn6 border-back text-big input-big" onclick="submitFrom()" value="更新订单"
                                   type="button">
                            <input class="btn btn4 border-back text-big input-big" onclick="history.go(-1)" value="返回"
                                   type="button">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>

</div>
</body>
</html>