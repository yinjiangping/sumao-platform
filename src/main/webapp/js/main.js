var g_body_page = $("body");
var g_baseUrl = "http://192.168.144.183:8093/mock/";
//var g_baseUrl = "http://www.189tel.cn/";//http://www.djtx.com.cn/


var g_nowPay_isLoading = false;
var g_isShangHai = true;
var g_openId = "";
var g_code = util.GetRequest()["code"];
var flowChannel=util.GetRequest()['state'];//请求渠道



//var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
//alert(g_code);
(function(page) {
    if (!page) {
        return;
    }
    $(function() {
        FastClick.attach(document.body);
    });
    //window.location.href="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx098de71c1f317199&secret=e58c1409f966a76dadd141a06ff87706&g_code="+aa+"&grant_type=authorization_g_code"
    var swiper = null;

    /**
     * 初始化视图
     * @return {[type]} [description]
     */
    onload = function() {
            wx.hideAllNonBaseMenuItem();
            //获取g_openId
            util.sendAjax({
                url: g_baseUrl + "queryOpenID",
                data: { code: g_code }
            }).done(function(data) {
                window.g_openId = data.result ? data.result.openID : "";
                if (g_openId != "") {
                    return data;
                } else {
                    layer.msg('网络异常');
                    //new Toast({ context: $('body'), message: '网络异常' }).show();
                }
            }).fail(function() {
                layer.msg('网络异常');
                //new Toast({ context: $('body'), message: '网络异常' }).show();
            }).done(function(data1) {
                util.sendAjax({
                    url: g_baseUrl + "queryMchnts",
                    data: { "openID": window.g_openId }
                }).done(function(data) {
                    if (data.success) {
                        var arr = data ? data.result : [];
                        window.queryMchnts_res = util.handleResult(arr); //处理数组
                        util.setEjs('users', 'swiper-wrapper', { g_isShangHai: true, items: queryMchnts_res.SH_LOCAL, items1: queryMchnts_res.ALL, items2: queryMchnts_res.SH_ALL});
                        initSwiper();
                    } else {
                        layer.msg('网络异常');
                        //new Toast({ context: $('body'), message: '网络异常' }).show();
                    }

                }).fail(function(data) {
                    layer.msg('网络异常');
                    //new Toast({ context: $('body'), message: '网络异常' }).show();
                });
            }).done(function(data) {
                //获取微信接口调用签名
                util.sendAjax({
                        url: g_baseUrl + "paySign",
                        data: {
                            "openID": window.g_openId,
                            "url": "http://www.djtx.com.cn/main.html"
                        }
                    })
                    .done(function(data) {
                        var res = data.result ? data.result : {};
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: res.appId, // 必填，公众号的唯一标识
                            timestamp: res.timeStamp, // 必填，生成签名的时间戳
                            nonceStr: res.nonceStr, // 必填，生成签名的随机串
                            signature: res.signature, // 必填，签名，见附录1
                            jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                    }).fail(function(data) {

                    });
            });

        }
        /**
         * 立即购买
         * @param  {[type]} ) {                   var v [description]
         * @return {[type]}   [description]
         */
    $("body").on("click", "#nowPay", function() {
        //util.openLoading();
        // if (g_nowPay_isLoading) {
        //     layer.msg('正在请求，请稍候');
        //     //new Toast({ context: $('body'), message: '正在请求，请稍候' }).show();
        //     return;
        // }
        // window.g_nowPay_isLoading = true;
        var v = $("#tel").val(); //电话号码
        if (!util.checkTelecom(v) || !util.checkTel(v)) {
            layer.alert('该号码暂不支持', {
                closeBtn: 0
            });
            //layer.msg('该号码暂不支持');
            //new Toast({ context: $('body'), message: '该号码暂不支持' }).show();
           // window.g_nowPay_isLoading = false;
            return;
        } else {
            var flow = "";
            var isLocalFlow=util.isLocalFlow();
            //判断充值流量类型
            // if (isLocalFlow==1) {
            //     flow = $("input[name='b-flow']:checked").val(); //本地流量
            // } else if(isLocalFlow==2){
            //     flow = $("input[name='b-flow1']:checked").val(); //全国流量
            // } else{
            //      flow = $("input[name='g-flow']:checked").val(); //全国流量
            // }
            if (isLocalFlow==1) {
                flow = $(".flow-active.flow-b input[name='b-flow']").val(); //本地流量
            } else if(isLocalFlow==2){
                flow = $(".flow-active.flow-b1 input[name='b-flow1']").val(); //全国流量
            } else{
                flow = $(".flow-active.flow-g input[name='g-flow']").val(); //全国流量
            }
            //alert(JSON.stringify(flow));
            if(!flow||flow=="undefined"){
                layer.alert('请选择流量包', {
                    closeBtn: 0
                });
                return;
            }
            var flowArr = flow.split(",");
            console.log(flowArr)
            if(flowArr.length<2){
                layer.alert('流量包不可用', {
                    closeBtn: 0
                });
                return;
            }
            // alert(JSON.stringify({
            //         "openID": window.g_openId,
            //         "phone": v,
            //         "flowID": flowArr[0],
            //         "flowCurrentCost": flowArr[1],
            //         "count": 1
            //     }));
           


            //下单动作
            util.sendAjax1({
                url: g_baseUrl + "order",
                data: {
                    "openID": window.g_openId,
                    "phone": v,
                    "flowID": flowArr[0],
                    "flowCurrentCost": flowArr[1],
                    "count": 1,
                    "flowChannel":flowChannel||""
                }
            }).done(function(data) {
                //window.g_nowPay_isLoading = false;
                var res = data.result ? data.result : {};
                if (!data.success && !res) {
                    layer.msg(data.errorMsg || "下单失败");
                    //new Toast({ context: $('body'), message: data.errorMsg || "下单失败" }).show();
                    return;
                }
                //alert(JSON.stringify(res))
                //alert(4)
                //alert(res.timeStamp)
                //调用微信收银台
                wx.ready(function() {
                    //alert("成功");
                    wx.chooseWXPay({
                        timestamp: res.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                        nonceStr: res.nonceStr, // 支付签名随机串，不长于 32 位
                        package: res.package_gjz, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                        signType: res.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                        paySign: res.paySign, // 支付签名
                        success: function(res) {
                            // 支付成功后的回调函数
                        }
                    });
                });
                wx.error(function() {
                    //alert("失败");
                });
            }).fail(function(e) {
                //alert(JSON.stringify(e));
                //alert("下单失败");
                //window.g_nowPay_isLoading = false;
            })


        }
    });
    /**
     * 监听文本框变化
     * @param  {[type]} ){                                              var v [description]
     * @param  {[type]} message:'该号码暂不支持'}).show();                                              return;            }            if(len [description]
     * @return {[type]}                              [description]
     */
    $("#tel").on("input paste", function() {
        var v = $(this).val();
        var len = v.length;
        if(v.length>11){
            v=$(this).val(v.substr(0,11));
            len = v.length;//充值
            return;
        }
        //如果长度为0，显示上海电信商品
        if (len <= 0) {
            if (window.g_isShangHai) {
                return false;
            } else {
                window.g_isShangHai = true;
                util.setEjs('users', 'swiper-wrapper', { g_isShangHai: true, items: queryMchnts_res.SH_LOCAL, items1: queryMchnts_res.ALL,items2: queryMchnts_res.SH_ALL });
                swiper.update(); //更新视图
                init(); //设置tab
                return;
            }
        }
        //长度大于3本地做判断
        if (len >= 3) {
            if (!util.checkTelecom(v)) {
                if (len > 3) {
                    return;
                }
                layer.msg('该号码暂不支持');
                //new Toast({ context: $('body'), message: '该号码暂不支持' }).show();
                return;
            }
            if (len == 7) {
                util.sendAjax({
                    url: g_baseUrl + "queryBin",
                    data: {
                        "phone": v,
                        "openID": window.g_openId
                    }
                }).done(function(data) {
                    if (data.success) {
                        var res = data.result ? data.result : {};
                        swiper.slideTo(0, 0); //切换到第一屏
                        if (res.phoneBelong == 0) { //上海本地
                            if (window.g_isShangHai) {
                                return false;
                            } else {
                                window.g_isShangHai = true;
                                util.setEjs('users', 'swiper-wrapper', { g_isShangHai: true, items: queryMchnts_res.SH_LOCAL, items1: queryMchnts_res.ALL,items2: queryMchnts_res.SH_ALL });
                            }
                        } else { //全国
                            if (!window.g_isShangHai) {
                                return false;
                            } else {
                                window.g_isShangHai = false;
                                util.setEjs('users', 'swiper-wrapper', { g_isShangHai: false, items1: queryMchnts_res.ALL });
                            }

                        }
                        swiper.update(); //更新视图
                        init(); //设置tab
                    } else {
                        layer.msg('网络异常');
                        //new Toast({ context: $('body'), message: '网络异常' }).show();
                        return;
                    }
                }).fail(function() {
                    layer.msg('网络异常');
                    //new Toast({ context: $('body'), message: '网络异常' }).show();
                })
            }
        } else {
            return;
        }
    });
    /**
     * 改变窗口大小
     * @return {[type]} [description]
     */
    window.onresize = function() {
        if (swiper) {
            init();
            // swiper.update(); //更新视图
        }

    }

    /**
     * 初始化swiper
     * @return {[type]} [description]
     */
    function initSwiper() {
        swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            // effect : 'flip',
            freeMode: false,
            paginationClickable: true,
            // noSwiping: true,
            preventClicks: false, //默认true
            autoHeight: true,
            onInit: init,
            onSlideChangeEnd: function(swiper) {
                console.log(swiper);
                console.log(swiper.activeIndex)
            }
        });
    }

    /**
     * swiper初始化后执行的函数
     * @return {[type]} [description]
     */
    function init() {
        setTimeout(function() {
            if ($("span.swiper-pagination-bullet").length >= 2) {
                $("span.swiper-pagination-bullet:nth-of-type(1)").html("上海本地");
                $("span.swiper-pagination-bullet:nth-of-type(2)").html("上海全国");
                $("span.swiper-pagination-bullet:nth-of-type(3)").html("全国流量");
            } else {
                $("span.swiper-pagination-bullet:nth-of-type(1)").html("全国流量");
            }
        }, 30);



        setRadio("b");
        setRadio("g");
    }
    /**
     * 切屏时执行的函数
     * @return {[type]} [description]
     */
    function reInit() {
        $("span.swiper-pagination-bullet:nth-of-type(1) ").html("全国流量");
    }
    /**
     * 设置单选按钮
     * @param {[type]} flag [description]
     */
    function setRadio(flag) {
        var flow = "";
        var flow1="";
        var checked = null;
        if (flag == "g") {
            flow = $(".flow-g");
            $("body").off("click", "input.g-flow").on("click ", "input.g-flow", function(e) {
                //checked = $("input[name='g-flow']:checked ");
                var that = $(this);
                flow.removeClass("flow-active");
                that.parents(".flow-g").addClass("flow-active");
                $("#flowDesc").html(that.attr("data-flowDesc")); //显示流量描述
            });
        } else  {
            flow = $(".flow-b");
            $("body").off("click", "input.b-flow").on("click ", "input.b-flow", function(e) {
                var that = $(this);
                flow.removeClass("flow-active");
                that.parents(".flow-b").addClass("flow-active");
                $("#flowDesc").html($(this).attr("data-flowDesc")); //显示流量描述
            });
            //上海全国
            flow1 = $(".flow-b1");
            $("body").off("click", "input.b-flow1").on("click ", "input.b-flow1", function(e) {
                var that = $(this);
                flow1.removeClass("flow-active");
                that.parents(".flow-b1").addClass("flow-active");
                $("#flowDesc").html($(this).attr("data-flowDesc")); //显示流量描述
            });
        }



    }

    // setTimeout(function() {

    //     //swiper.update(true);
    //     // swiper.removeSlide(0); 
    //     // reInit();
    // }, 3000)
})(g_body_page.hasClass("p-main"));

(function(page) {
    if (!page) {
        return;
    }



    //start 上拉加载更多
    var myScroll;
    var pullDownEl, pullDownL;
    var pullUpEl, pullUpL;
    var Downcount = 0,
        Upcount = 0;
    var loadingStep = 0; //加载状态0默认，1显示加载状态，2执行加载数据，只有当为0时才能再次加载，这是防止过快拉动刷新 
    var items = [];

    function pullDownAction() { //下拉事件  
        pullDownEl.removeClass('loading');
        // pullDownL.html('下拉显示更多...');
        pullDownEl['class'] = pullDownEl.attr('class');
        pullDownEl.attr('class', '').hide();
        myScroll.refresh();
        loadingStep = 0;
    }
    /**
     *  上拉加载更多
     * @return {[type]} [description]
     */
    function pullUpAction() { //上拉事件  
        util.sendAjax({
            url: g_baseUrl + "queryOrderList",
            data: {
                "openID": window.g_openId,
                "phone": "",
                "pageNumber": window.queryOrderListPageNumber = window.queryOrderListPageNumber + 1,
                "pageSize": 20
            }
        }).done(function(data) {
            if (data.success) {
                var res = data ? data.result : {};
                if (typeof res == "object") {
                    queryOrderList_res.push.apply(queryOrderList_res, res);
                }
                if (queryOrderList_res) {
                    util.setEjs('prelist', 'add', { items: queryOrderList_res });
                }
            } else {
                layer.msg(data.errorMsg || '正在努力查询');
                //new Toast({ context: $('body'), message: data.errorMsg || '正在努力查询' }).show();
            }
        }).fail(function() {
            layer.msg('网络异常');
            //new Toast({ context: $('body'), message: '网络异常' }).show();
        });


        pullUpEl.removeClass('loading');
        // pullUpL.html('上拉显示更多...');
        pullUpEl['class'] = pullUpEl.attr('class');
        pullUpEl.attr('class', '').hide();
        myScroll.refresh();
        loadingStep = 0;
    }

    function loaded1() {
        pullDownEl = $('#pullDown');
        pullDownL = pullDownEl.find('.pullDownLabel');
        pullDownEl['class'] = pullDownEl.attr('class');
        pullDownEl.attr('class', '').hide();

        pullUpEl = $('#pullUp');
        pullUpL = pullUpEl.find('.pullUpLabel');
        pullUpEl['class'] = pullUpEl.attr('class');
        pullUpEl.attr('class', '').hide();

        myScroll = new IScroll('#content', {
            probeType: 2, //probeType：1对性能没有影响。在滚动事件被触发时，滚动轴是不是忙着做它的东西。probeType：2总执行滚动，除了势头，反弹过程中的事件。这类似于原生的onscroll事件。probeType：3发出的滚动事件与到的像素精度。注意，滚动被迫requestAnimationFrame（即：useTransition：假）。  
            scrollbars: true, //有滚动条  
            mouseWheel: true, //允许滑轮滚动  
            fadeScrollbars: true, //滚动时显示滚动条，默认影藏，并且是淡出淡入效果  
            bounce: true, //边界反弹  
            interactiveScrollbars: true, //滚动条可以拖动  
            shrinkScrollbars: 'scale', // 当滚动边界之外的滚动条是由少量的收缩。'clip' or 'scale'.  
            click: true, // 允许点击事件  
            keyBindings: true, //允许使用按键控制  
            momentum: true // 允许有惯性滑动  
        });
        //滚动时  
        myScroll.on('scroll', function() {
            if (loadingStep == 0 && !pullDownEl.attr('class').match('flip|loading') && !pullUpEl.attr('class').match('flip|loading')) {
                if (this.y > 5) {
                    // //下拉刷新效果  
                    // pullDownEl.attr('class', pullUpEl['class'])
                    // pullDownEl.show();
                    // myScroll.refresh();
                    // pullDownEl.addClass('flip');
                    // pullDownL.html('准备刷新...');
                    // loadingStep = 1;
                } else if (this.y < (this.maxScrollY - 5)) {
                    //上拉刷新效果  
                    pullUpEl.attr('class', pullUpEl['class'])
                    pullUpEl.show();
                    myScroll.refresh();
                    pullUpEl.addClass('flip');
                    // pullUpL.html('准备刷新...');
                    loadingStep = 1;
                }
            }
        });
        //滚动完毕  
        myScroll.on('scrollEnd', function() {
            if (loadingStep == 1) {
                if (pullUpEl.attr('class').match('flip|loading')) {
                    pullUpEl.removeClass('flip').addClass('loading');
                    // pullUpL.html('Loading...');
                    loadingStep = 2;
                    pullUpAction();
                }
                // else if (pullDownEl.attr('class').match('flip|loading')) {
                //     pullDownEl.removeClass('flip').addClass('loading');
                //     pullDownL.html('Loading...');
                //     loadingStep = 2;
                //     pullDownAction();
                // }
            }
        });
    }
    //end 上拉加载更多

    /**
     * 页面初始化
     * @return {[type]} [description]
     */
    onload = function() {
            window.g_openId=$.cookie("g_openId");
            if(!!$.cookie("g_openId")){
                util.sendAjax({
                    url: g_baseUrl + "queryOrderList",
                    data: {
                        "openID": window.g_openId,
                        "phone": "",
                        "pageNumber": window.queryOrderListPageNumber = 1,
                        "pageSize": 20
                    }
                }).done(function(data) {
                    console.log(data)
                    if (data.success) {
                        window.queryOrderList_res = data.result ? data.result : [];
                        if (queryOrderList_res) {
                            util.setEjs('prelist', 'add', { items: queryOrderList_res });
                            loaded1();
                        }
                    } else {
                        layer.msg(data.errorMsg || '正在努力查询');
                        //new Toast({ context: $('body'), message: data.errorMsg || '正在努力查询' }).show();
                    }

                }).fail(function() {
                    layer.msg('网络异常，请稍候再试');
                    //new Toast({ context: $('body'), message: '网络异常，请稍候再试' }).show();
                });
                return;
            }
            //获取g_openId
            util.sendAjax({
                url: g_baseUrl + "queryOpenID",
                data: { code: g_code }
            }).done(function(data) {
                console.log(data);
                window.g_openId = data.result ? data.result.openID : "";
                $.cookie("g_openId",window.g_openId);
                if (g_openId != "") {
                    return data;
                } else {
                    layer.msg('网络异常');
                    //new Toast({ context: $('body'), message: '网络异常' }).show();
                }
            }).fail(function() {
                layer.msg('网络异常');
                //new Toast({ context: $('body'), message: '网络异常' }).show();
            }).done(function(data) {
                //获取订单列表
                if(!data.success||!window.g_openId){
                    layer.msg("网络异常");
                    return;
                }
                util.sendAjax({
                    url: g_baseUrl + "queryOrderList",
                    data: {
                        "openID": window.g_openId,
                        "phone": "",
                        "pageNumber": window.queryOrderListPageNumber = 1,
                        "pageSize": 20
                    }
                }).done(function(data) {
                    console.log(data)
                    if (data.success) {
                        window.queryOrderList_res = data.result ? data.result : [];
                        if (queryOrderList_res) {
                            util.setEjs('prelist', 'add', { items: queryOrderList_res });
                            loaded1();
                        }
                    } else {
                        layer.msg(data.errorMsg || '正在努力查询');
                        //new Toast({ context: $('body'), message: data.errorMsg || '正在努力查询' }).show();
                    }

                }).fail(function() {
                    layer.msg('网络异常，请稍候再试');
                    //new Toast({ context: $('body'), message: '网络异常，请稍候再试' }).show();
                });
            });
        }
        /**
         * 根据电话号码，查询相应的订单
         * @param  {[type]} ) {                   var v [description]
         * @return {[type]}   [description]
         */
    $("#tel").on("input paste", function() {
        var v = $(this).val();
        var len = v.length;
        if (len < 3) {
            return;
        }
        if (util.checkTelecom(v)) { //检查前3位
            if (len < 11) {
                return;
            }
            if (util.checkTel(v)) {
                util.sendAjax({
                    url: g_baseUrl + "queryOrderList",
                    data: {
                        "openID": window.g_openId,
                        "phone": v,
                        "pageNumber": window.queryOrderListPageNumber = 1,
                        "pageSize": 20
                    }
                }).done(function(data) {
                    layer.msg('查询成功');
                    //new Toast({ context: $('body'), message: '查询成功' }).show();
                    window.queryOrderList_res = (data.result ? data.result : {}).orderList;
                    if (queryOrderList_res) {
                        util.setEjs('prelist', 'add', { items: queryOrderList_res });
                        console.log(queryOrderList_res);
                        if (!myScroll) {
                            loaded1();

                        } else {
                            myScroll.refresh();
                        }
                    } else {
                        layer.msg('网络异常，请稍候再试');
                        //new Toast({ context: $('body'), message: '网络异常，请稍候再试' }).show();
                    }
                }).fail(function() {
                    layer.msg('网络异常，请稍候再试');
                    //new Toast({ context: $('body'), message: '网络异常，请稍候再试' }).show();
                })
            } else {
                layer.msg('请输入正确的手机号');
                //new Toast({ context: $('body'), message: '请输入正确的手机号' }).show();
            }
        } else {
            if (len == 3) {
                layer.msg('该手机号码暂不支持');
                //new Toast({ context: $('body'), message: '该手机号码暂不支持' }).show();
            }

        }
    });
    /**
     * 查看订单详情
     * @param  {[type]} e){                 } [description]
     * @return {[type]}      [description]
     */
    $("body").on("click", ".order-item", function(e) {
        layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        var that = $(this);
        var orderID = that.attr("data-orderID"); //订单编号
        var flowTitle = that.attr("data-flowTitle"); //订单描述
        var orderTime = that.attr("data-orderTime"); //下单时间
        var transStatus = that.attr("data-transStatus"); //订单状态
        var flowDesc = that.attr("data-flowDesc"); //订单描述
        var phone = that.attr("data-phone"); //手机号

        //跳转到订单详情页面
        window.location.href = "./detail.html?orderID=" + orderID + "&flowTitle=" + flowTitle + "&orderTime=" + orderTime + "&transStatus=" + transStatus +"&flowDesc=" +flowDesc + "&phone=" + phone;
    });

    //阻止默认滚动事件
    document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);



})(g_body_page.hasClass("p-list"));

(function(page) {
    if (!page) {
        return;
    }
    var params = util.GetRequest();
    var item = {};
    if (typeof params == "object") {
        item.orderID = params["orderID"];
        item.flowTitle = params["flowTitle"];
        item.orderTime = new Date(parseInt(params["orderTime"])).Format('yyyy-MM-dd hh:mm:ss');
        item.transStatus = params["transStatus"];
        item.flowDesc = params["flowDesc"];
        item.phone = params["phone"];
    }
    util.setEjs('predetail', 'detail', { item: item });

})(g_body_page.hasClass("p-detail"))
