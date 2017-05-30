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

<body>
<script type="text/javascript">
    function submitFrom() {

        var orderNo = $("#orderNo").val();
        if (orderNo == "" || orderNo == undefined || orderNo == null) {
            alert("请输入订单编号");
            return;
        }

        var expressName = $("#expressName").val();
        if (expressName == "" || expressName == undefined || expressName == null) {
            alert("请输入快递公司名称");
            return;
        }

        var expressNo = $("#expressNo").val();
        if (expressNo == "" || expressNo == undefined || expressNo == null) {
            alert("请输入运单号");
            return;
        }

        var requestParams = "?orderNo=" + $("#orderNo").val() + "&expressName=" + encodeURI($("#expressName").val())+ "&expressNo=" + $("#expressNo").val();
        htmlobj=$.ajax({url:$("#myform").attr("action") + requestParams,async:false});
        alert(htmlobj.responseText);
    }
</script>
<div class="main-wrap">

    <div class="crumb-wrap">
        <div class="crumb-list"><i class="icon-font"></i><a href="/jscss/admin/design/">首页</a><span class="crumb-step">&gt;</span><a
                class="crumb-name" href="/jscss/admin/design/">订单管理</a><span
                class="crumb-step">&gt;</span><span>更新订单</span></div>
    </div>
    <div class="result-wrap">
        <div class="result-content">
            <form action="${pageContext.request.contextPath}/orderDelivery" method="post" id="myform" name="myform"
                  enctype="multipart/form-data">
                <table class="insert-tab" width="100%">
                    <tbody>
                    <tr>
                        <td><i class="require-red">*</i>订单编号：</td>
                        <td>
                            <input class="common-text required" id="orderNo" disabled="disabled" name="orderNo" size="50"
                                   value="${paramMaps.orderNo}" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td><i class="require-red">*</i>快递公司：</td>
                        <td>
                            <input class="common-text required" id="expressName" name="expressName" size="50"
                                   value="${paramMaps.expressName}" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td><i class="require-red">*</i>运单号：</td>
                        <td>
                            <input class="common-text required" id="expressNo" name="expressNo" size="50"
                                   value="${paramMaps.expressNo}" type="text">
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input class="btn btn-primary btn6 mr10" onclick="submitFrom()" value="更新订单" type="button">
                            <input class="btn btn6" onclick="history.go(-1)" value="返回" type="button">
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