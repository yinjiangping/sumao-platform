//133、153、180、181、182、189

window.alert = function(str){
    layer.alert(str, {
        closeBtn: 0
    });
}

/**
 * 工具函数
 * @param  {[type]} argument [description]
 * @return {[type]}          [description]
 */
var Util = function(argument) {
        // body...
    }
    /**
     * 判断是否为电信号码
     * @param  {[type]} tel [description]
     * @return {[type]}     [description]
     */
Util.prototype.checkTelecom = function(tel) {
    //目前移动、联通、电信三大运营商的手机号段如下：
    //1、移动号段有134,135,136,137, 138,139,147,150,151, 152,157,158,159,178,182,183,184,187,188。
    //2、联通号段有130，131，132，155，156，185，186，145，176。
    //3、电信号段有133，153，177，180，181，189。
    var flag = /^(133|153|180|181|182|189|177|173)/;
    if (!flag.test(tel)) {
        return false;
    }
    return true;
};

Util.prototype.removeObjWithArr = function (_arr,id) {
    var length = _arr.length;
    for(var i = 0; i < length; i++)
    {
        if(_arr[i].id == id)
        {
            if(i == 0)
            {
                _arr.shift(); //删除并返回数组的第一个元素
                return _arr;
            }
            else if(i == length-1)
            {
                _arr.pop();  //删除并返回数组的最后一个元素
                return _arr;
            }
            else
            {
                _arr.splice(i,1); //删除下标为i的元素
                return _arr;
            }
        }
    }
};
Util.prototype.getLocalKey=function(k){
    return window.localStorage.getItem(k);
}
Util.prototype.setLocalKey=function(k,v){
    return window.localStorage.setItem(k,v);
}
Util.prototype.setSessionKey = function(k,v){
    return window.sessionStorage.setItem(k,v);
}
Util.prototype.getSessionKey = function(k){
    return window.sessionStorage.getItem(k);
}

Util.prototype.checkTel=function(tel){
	var flag=/^\d{11}$/;
	if(flag.test(tel)){
		return true;
	}
	return false;
}
/**
 * 发送http请求
 * @param  {[type]} obj [发送对象]
 * @return {[type]}     [deferred]
 */
Util.prototype.sendAjax = function(obj) {
    layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    var that = this;
    //重新封装入参
    var o_obj = {};
    o_obj.type = "post";//
    //o_obj.type = "get";//
    o_obj.url = obj.url;
    o_obj.data = that.sendData(obj.data);
    //alert("入参数\n"+o_obj.url+"\n"+JSON.stringify(o_obj.data));
    //封装参数结束
    //发起http请求
    return $.ajax({
        type: o_obj.type,
        url: o_obj.url,
        dataType: "json",
        cache: false,
       // contentType : "application/json"
        headers: {
           contentType : "application/x-www-form-urlencoded; charset=UTF-8",  
        },
        data: o_obj.data
    }).done(function(data){
        //alert("hh"+JSON.stringify(data))
    }).complete(function(){
        layer.closeAll();
    });
};
/**
 * 发送http请求
 * @param  {[type]} obj [发送对象]
 * @return {[type]}     [deferred]
 */
Util.prototype.sendAjax1 = function(obj) {
    layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    var that = this;
    //重新封装入参
    var o_obj = {};
    o_obj.type = "post";
    o_obj.url = obj.url;
   // o_obj.data = that.sendData(obj.data);
    //alert("入参数1\n"+o_obj.url+"\n"+JSON.stringify(obj.data));
    //封装参数结束
    //发起http请求
    return $.ajax({
        type: o_obj.type,
        url: o_obj.url,
        dataType: "json",
        cache: false,
        contentType : "application/json",  
        headers: {
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",  
        },
        data: JSON.stringify(obj.data)
    }).done(function(data){
        //alert("ww"+JSON.stringify(data))
    }).complete(function(){
        layer.closeAll();
    })
};
/**
 * 发送http请求
 * @param  {[type]} obj [发送对象]
 * @return {[type]}     [deferred]
 */
Util.prototype.sendAjax2 = function(obj) {
    layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    var that = this;
    //重新封装入参
    var o_obj = {};
    o_obj.type = "post";
    o_obj.url = obj.url;
   // o_obj.data = that.sendData(obj.data);
    //alert("入参数1\n"+o_obj.url+"\n"+JSON.stringify(obj.data));
    //封装参数结束
    //发起http请求
    return $.ajax({
        type: o_obj.type,
        url: o_obj.url,
        dataType: "text",
        cache: false,
        //contentType : "application/json",  
        headers: {
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",  
        },
        data: JSON.stringify(obj.data)
    }).done(function(data){
        //alert("ww"+JSON.stringify(data))
    }).complete(function(){
        layer.closeAll();
    })
};
Util.prototype.isLocalFlow=function(){
	var dom=$(".swiper-pagination-bullet-active").html();
	if(dom.indexOf("本地")>=0){
		return 1;
	}else if(dom.indexOf("上海全国")>=0){
		return 2;
	}else{
        return 3;
    }

}
 Util.prototype.GetRequest=function() {
    var url = window.decodeURIComponent(location.search); //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
};
/**
 * 处理商品列表
 * @param  {[type]} arr [description]
 * @return {[type]}     [description]
 */
Util.prototype.handleResult = function(arr) {
        var obj = {
            "SH_LOCAL": [],
            "SH_ALL": [],
            "ALL": []
        };
        arr.forEach(function(k) {
            if (k.flowBelong == "SH_LOCAL") {
                obj.SH_LOCAL.push(k);
                return;
            }
            if (k.flowBelong == "SH_ALL") {
                obj.SH_ALL.push(k);
                return;
            }
            if (k.flowBelong == "ALL") {
                obj.ALL.push(k);
                return;
            }
        });
        return obj;

    };
    /**
     * 处理发送数据
     * @param  {[type]} data [description]
     * @return {[type]}      [description]
     */
Util.prototype.sendData = function(data) {
    var res = "";
    $.each(data, function(k, v) {
        res += "&" + k + "=" + (v||"");
    });
    if (res.length > 0) {
        res = res.substr(1);
    }
    return res;
}

/**
 *将数据绑到模板添加到指定容器上
 * */
Util.prototype.fetchTpl=function(containerId, tplUrl, data) {
    var $container = $("#" + containerId);
    $container.empty();
    var tplCont = $container.setTemplateURL(tplUrl);
    tplCont.processTemplate(data);
    //window.location.hash="/"+tplUrl.substring(tplUrl.lastIndexOf("/")+1,tplUrl.lastIndexOf("."));

}

Util.prototype.setEjs = function(prid, toid, data) {
    var html = ejs.render($('#' + prid).html(), data);
    $("#" + toid).html(html);
}

Util.prototype.openLoading=function(){

    var body=$("body");
    $("<div class='loading'></div>").appendTo(body);
}
Util.prototype.closeLoading=function(){
    $(".loading").remove();
}

window.util = new Util();

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) 
{ //author: meizz
  var o = { 
    "M+" : this.getMonth()+1,                 //月份
    "d+" : this.getDate(),                    //日
    "h+" : this.getHours(),                   //小时
    "m+" : this.getMinutes(),                 //分
    "s+" : this.getSeconds(),                 //秒
    "q+" : Math.floor((this.getMonth()+3)/3), //季度
    "S"  : this.getMilliseconds()             //毫秒
  }; 
  if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
  return fmt; 
}


