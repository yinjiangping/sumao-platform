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
                $("#freightAmt").html(json.freightAmt);
                $("#deliverType").html(getDeliverType(json.deliverType));
                $("#putOrderTime").html(json.putOrderTime);
                $("#state").html(json.state);
                $("#resCode").html(json.resCode);
                $("#resDesc").html(json.resDesc);
                $("#sName").html(json.sendName);
                $("#rName").html(json.receiveName);
                $("#rPhone").html(json.receivePhone);
                $("#rAddress").html(json.receiveAddress);
                $("#showData").html(json.expressInfo);
                if (json.state == "支付成功") {
                    $("#beginMade").css('display', '');
                }
                var htmlContext = "<br><br><table class='result-tab' width='100%'><tbody  id='img_list'>"
                var htmlContext = htmlContext + "<tr><td>图片地址</td><td>图片名称</td><td>图片单价</td><td>图片尺寸</td><td>图片数量</td><td>操作</td></tr>"
                $.each(json.imagesResList, function (index, item) {
                    var imgUrl = item.img;
                    htmlContext += "<tr><td><img style='width:70px;height:50px;' src='" + imgUrl + "' /></td><td>"+imgUrl.substring(imgUrl.lastIndexOf('/')+1)+"</td><td>" + item.price + "</td><td>" + item.picSize + "</td><td>" + item.number + "</td><td><input class='btn btn4 border-back text-big input-big' onclick= downloadImage('" + item.img + "') value='下载' type=button /></td></tr>"
                });
                $("#list").html("</tbody>"+htmlContext);
            },
            error: function () {
                return;
            }
        });
    }

    function getDeliverType(type) {
        var deliverType = "未知"
        if (type == "0") {
            deliverType = "未选择"
        } else if (type == "1") {
            deliverType = "快递上门"
        } else if (type == "2") {
            deliverType = "上门自提"
        }
        return deliverType;
    }
    function downloadImage(src) {
        var a = $("<a></a>").attr("href", src).attr("download", "img.png").appendTo("body");
        a[0].click();
        a.remove();
    }

    function batchDownload() {
        var imgArray = new Array();
        var trList = $("#img_list").children("tr")
        for (var i = 1; i < trList.length; i++) {
            var tdArr = trList.eq(i).find("td");
            var imgUrl = tdArr.eq(0).find('img').attr("src"); //图片地址
            imgArray[ i - 1 ] = imgUrl;
        }
        var dNum = 0;
        imgArray.map(function (i) {
            var a = document.createElement('a');
            a.setAttribute('download', '');
            a.href = i;
            document.body.appendChild(a);
            a.click();
            dNum ++;
            });
        alert("列表共"+imgArray.length+"张图片，已批量下载"+dNum+"张图片");
    }


        function updateOrder() {
            var processKey = "状态";
            var processValue = "正在制作";
            var requestParams = "?orderNo=" + '${paramMaps.orderNo}' + "&cType=cz" + "&processKey=" + encodeURI(processKey) + "&processValue=" + encodeURI(processValue);
            htmlobj = $.ajax({url: '${pageContext.request.contextPath}/orderDelivery' + requestParams, async: false, cache: false});
            if (htmlobj.responseJSON == true) {
                showData('${paramMaps.orderNo}');
            } else {
                $("#showData").html(processKey + ":" + "接单失败");
                $("#beginMade").css('display', 'none');
            }
        }
</script>
<div class="main-wrap">

    <div class="crumb-wrap">
        <div class="crumb-list">
            <span class="crumb-name">首页</span><span class="crumb-step">&gt;</span><span
                class="crumb-name">订单管理</span><span
                class="crumb-step">&gt;</span><span>订单详情</span>
        </div>
    </div>
    <div class="result-wrap">
        <div class="result-content">
            <form action="${pageContext.request.contextPath}/orderDelivery" method="post" id="myform" name="myform"
                  enctype="multipart/form-data">
                <table class='result-tab' width="100%" border="1" cellspacing="0" cellpadding="0"
                       style="border-collapse:collapse">
                    <tbody>
                    <tr>
                        <td><i class="require-red"></i>订单编号</td>
                        <td><i class="require-red"></i>商品金额</td>
                        <td><i class="require-red"></i>运费</td>
                        <td><i class="require-red"></i>配送类型</td>
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
                            <span id="freightAmt"></span>
                        </td>
                        <td>
                            <span id="deliverType"></span>
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
                        <td colspan="2"><i class="require-red"></i>备注</td>
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
                        <td colspan="2">
                            <span style="font-size:18px;color:dodgerblue;" id="showData"></span>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="result-content" id="list">
                </div>
                <br>
                <H5 align="center"><input class="btn btn4 border-back text-big input-big" id="beginMade"
                                          style="display: none" onclick="updateOrder()"
                                          value="接单制作" type="button">&nbsp;&nbsp;
                    <input
                        class="btn btn4 border-back text-big input-big"
                        onclick="batchDownload()"
                        value="批量下载" type="button">&nbsp;&nbsp;<input
                        class="btn btn4 border-back text-big input-big" onclick="history.go(-1)"
                        value="返回" type="button"></H5>
            </form>
        </div>
    </div>

</div>
</body>
</html>