<%@ page import="team.yqby.platform.common.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title></title>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/2.0.0/jquery.js"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
    <script language="javascript" type="text/javascript"
            src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css"/>

<body onload="showData('','',document.getElementById('startDate').value,document.getElementById('endDate').value)">
<script type="text/javascript">

    function operaRequest(ips) {
        window.location.href = $(ips).attr("label");
    }

    function submitFrom(ips) {
        htmlobj = $.ajax({url: $(ips).attr("label"), async: false});
        alert(htmlobj.responseText);
        showData('');
    }

    function showData(orderNo, process, startDate, endDate) {
        $.ajax({
            url: '${pageContext.request.contextPath}/queryAllOrder?orderNo=' + orderNo + "&process=" + process + "&startDate=" + startDate + "&endDate=" + endDate,
            type: 'GET',
            dataType: 'text',
            timeout: 1000,
            cache: false,
            beforeSend: LoadFunction,
            //加载执行方法
            error: erryFunction,
            //错误执行方法
            success: succFunction //成功执行方法
        })
    }

    function LoadFunction() {
        $("#list").html('<div style="text-align:center;"><span style="cursor: pointer;color: green;vertical-align:middle;">请稍等,数据正在加载中...</span></div>');
    }
    function erryFunction() {
        $("#list").html('<div style="text-align:center;"><span style="cursor: pointer;color: red;vertical-align:middle;">服务器未响应结果</span></div>');
    }
    function succFunction(tt) {
        var json = eval(tt); //数组
        var htmlContext = "<table class='result-tab' width='100%'>"
        var htmlContext = htmlContext + "<tr><td>订单号</td><td>金额</td><td>订单状态</td><td>创建订单时间</td><td>支付订单时间</td><td>操作</td></tr>"
        $.each(json, function (index, item) {
            //循环获取数据
            var orderNo = json[index].orderNo;
            var price = json[index].price;
            var state = json[index].state;
            var createTime = json[index].createTime;
            var putOrderTime = json[index].putOrderTime;
            htmlContext += "<tr><td>" + orderNo + "</td><td>" + price + "</td><td>"+getOrderStatus(state)+"</td><td>"+createTime+"</td><td>"+putOrderTime+"</td><td>"
            var requestParam = '?pageId=1011&pageUrl=order/edit&orderNo=' + orderNo
            if(state == "DELIVERY_FAIL" || state == "PAY_SUCCESS"){
                htmlContext += "<input class='btn text-big input-big btn2' label='${pageContext.request.contextPath}/forwardFunPage" + requestParam + "' onclick='operaRequest(this)' value='发货' type='button'>&nbsp;"
            }
            var requestParam = '?pageId=1011&pageUrl=order/detail&orderNo=' + orderNo
            htmlContext += "<input class='btn text-big input-big btn2' label='${pageContext.request.contextPath}/forwardFunPage" + requestParam + "' onclick='operaRequest(this)' value='订单详情' type='button'>"
            htmlContext += "</td></tr>"
        });
        $("#list").html(htmlContext);
    }

    function getOrderStatus(state){
        var orderState = "未知"
        if(state == "INIT"){
            orderState = "初始化"
        }else if(state == "WAIT_PAY"){
            orderState = "下单成功"
        }else if(state == "ORDER_FAIL"){
            orderState = "下单失败"
        }else if(state == "PAY_SUCCESS"){
            orderState = "支付成功"
        }else if(state == "PAY_FAIL"){
            orderState = "支付失败"
        }else if(state == "DELIVERY_SUCCESS"){
            orderState = "已发货"
        }else if(state == "DELIVERY_FAIL"){
            orderState = "发货失败"
        }
        return orderState;
    }

    function getNowFormatDate(dayNum) {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + ( strDate - dayNum);
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
        return currentdate;

    }
</script>
<div class="main-wrap">
    <div class="crumb-wrap">
        <div class="crumb-list">
            <i class="icon-font"></i>首页
<span class="crumb-step">&gt;
        </span>
            <span class="crumb-name">订单管理</span>
        </div>
    </div>
    <div class="result-wrap">
        <div class="result-title">
            <div class="result-list">
                <table class="search-tab">
                    <tr>
                        <td>订单号 :</td>
                        <td>
                            <input id="orderNo" class="common-text" name="keywords" value="" type="text">
                        </td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>状态 :</td>
                        <td>
                            <select id="process" name="process">
                                <option value="">请选择</option>
                                <option value="INIT">初始状态</option>
                                <option value="WAIT_PAY">下单成功</option>
                                <option value="PAY_SUCCESS">支付成功</option>
                                <option value="PAY_FAIL">支付失败</option>
                                <option value="DELIVERY_SUCCESS">已发货</option>
                                <option value="DELIVERY_FAIL">发货失败</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>开始日期:</td>
                        <td>
                            <input id="startDate" class="common-text Wdate" name="startDate" value="<%=DateUtil.getDateAfter(0)%>"
                                   type="text"
                                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   >
                        </td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>结束日期:</td>
                        <td>
                            <input id="endDate" class="common-text Wdate" name="endDate" value="<%=DateUtil.getDateAfter(0)%>"
                                   type="text"
                                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                        </td>
                        <td>
                            <input class="btn btn4 border-back text-big input-big" aria
                                   onclick="showData(document.getElementById('orderNo').value,document.getElementById('process').value,document.getElementById('startDate').value,document.getElementById('endDate').value)"
                                   value="查询" type="button">
                        </td>
                    </tr>
                </table>
                <div class="result-content" id="list">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>