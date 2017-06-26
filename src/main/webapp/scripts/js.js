//var g_baseUrl = "http://192.168.144.183:8093/mock/";
//var g_baseUrl = "http://www.189tel.cn/";//http://www.djtx.com.cn/
var g_baseUrl = "http://www.sumaophoto.net/webChat/";
//var g_baseUrl = "/json/";
var g_code = util.GetRequest()["code"];
var g_price = {}; //价格
(function() {
    if (window.location.href.indexOf("index.html") < 0 && !util.getSessionKey("openID")) {
         window.location.replace("./index.html");
         return;
    }
    $(function() {
         FastClick.attach(document.body);
    });
})();

//首页添加图片管理
var imgMag = (function() {
    //减小
    $(".box").on("click", ".btn-minus", function() {
        var Thisipt = $(this).siblings(".show-count");
        if (parseInt(Thisipt.text()) !== 1) {
            Thisipt.text(parseInt(Thisipt.text()) - 1);
            setTotal();
            setTotalAll();
        }
        return false;
    });
    //增加
    $(".box").on("click", ".btn-plus", function() {
        var Thisipt = $(this).siblings(".show-count");
        Thisipt.text(parseInt(Thisipt.text()) + 1);
        setTotal();
        setTotalAll();
        return false;
    });
    //手动选择尺寸
    $(".box").on("click", ".canclick", function() {
        var This = $(this);
        var ThisSize = This.attr("data-value");
        var id=This.parents("li").attr("id");
        ChoiceSize(id, ThisSize);
    });
    //不能选提示
    $(".box").on("click", ".errtips", function() {
        layer.alert('原图精度过低，不适合冲印这么大，茂茂做不到啊！', {
            closeBtn: 0
        });
    });
    //触发上传
    $(".box").on("change", "input", function() {
        var This = $(this);

        if (This.val()) {
            var file = This[0].files;
            //count=0;//初始化
            //countUp=file.length;//最大长度
            //var file = This[0].files[0];
            if (checkTypeImage(file)) {
                //for(var i=0;i<file.length;i++){
                //  upFile(This,file[i],file[i].size);
                //}
                upFile(This, file); //上传图片
            } else {
                layer.alert('请确保文件为图像类型', {
                    closeBtn: 0
                });
                return false;
            }
        }
    });
    //发送删除图片请求
    $(".box").on("click", ".close", function() {
        var This = $(this);
        removeImg(This);
        util.setTop(0);//重置上传按钮位置
        var Id = This.parents("li").attr("data-id");
        $.post(g_baseUrl + 'deletePic', { 'fileId': Id }, function(data) {
            if (data != 'false') { //false
                //removeImg(This);
                //util.setTop(0);//重置上传按钮位置
                //将本地数据del
                //arrFiles
                //arrFiles = util.removeObjWithArr(arrFiles,Id);
            } else {
                //layer.alert('删除失败', {
                //    closeBtn: 0
                //});
            }
        }, "text");
    });

    //还可添加数量
     function cunt() {
         var number = 36;
         $(".box li").each(function() {
             if (!$(this).find(".picture").is(":hidden")) {
                 number -= Number($(".show-count", $(this)).text());
             }
         });
         //$(".box li:last").find(".inner").find("p").text('还可添加' + number + '张相片');
         return number;
     }
    //单个商品总金额
    window.setTotal=function() {
        $(".box li").each(function() {
            var totalprice = parseFloat($(".show-count", $(this)).text()) * parseFloat($(".Unit-Price", $(this)).text());
            //console.log(totalprice);
            $(".total-single", $(this)).text(totalprice.toFixed(2));
        });
    }
    //全部商品总金额
    window.setTotalAll = function() {
        var sum = 0.00;
        var number = 0;
        $(".box li").each(function() {
            if (!$(this).find(".picture").is(":hidden")) {
                sum += Number($(".total-single", $(this)).text());
                number += Number($(".show-count", $(this)).text());
            }
        });
        $("#totalPrice").text(sum.toFixed(2));
        $("#totalcount").text(number);
    }
    setTotal(); //初始化
    setTotalAll(); //初始化

    //插入一个添加按钮
    window.addItem = function() {
        //$(".downUpload").remove();//删除先有的上传节点
        var nub = $(".box li").length;
        var overnub = 36 - nub;
        var LiHtml = '<li data-id="' + nub + '" id="downUpload"><div class="inner inner2"><div class="u-upload u-upload2"><button type="button" id="pickfiles">点击上传</button></div></div><div class="li-bj hide clearfix"><div class="picture"><span><img  src="images/photo.png"><a href="javascript:" class="close"></a></span></div><div class="inner-bar"><div class="amount">冲洗数量&emsp;<a href="javascript:" class="btn-minus"></a><span class="show-count">1</span><a href="javascript:" class="btn-plus"></a>&emsp;￥<span class="Unit-Price">0.99</span> /张<span class="hide total-single">0.00</span><!--单个商品总价--></div><dl class="size"><dd class="curr" data-value="5">5寸</dd><dd data-value="6">6寸</dd><dd data-value="7">7寸</dd><dd data-value="10">10寸</dd></dl></div></div></li>';
        if (nub < 36) {
            $(".box ul").append(LiHtml);
        }
    }
    window.removeItem = function () {
        $("#upUpload").remove();
    }
    //回到底部
    window.scrollBottom = function() {
        setTimeout(function() {
            var h = $(document).height() - $(window).height();
            //$(document).scrollTop(h);
            $('body,html').animate({ scrollTop: h }, 500);
        }, 10);
    }
    //选择尺寸计算价格
    window.ChoiceSize = function(id, szval) {
        var uP = $("#"+id).find(".Unit-Price");
        var size = parseInt(szval);
        $("#"+id).find("dd[data-value=" + szval + "]").addClass("curr").siblings("dd").removeClass("curr");
        
        switch (size) {
            case 5:
                uP.text(g_price.goods["5c"]);
                break;
            case 6:
                uP.text(g_price.goods["6c"]);
                break;
            case 7:
                uP.text(g_price.goods["7c"]);
                break;
            case 10:
                uP.text(g_price.goods["10c"]);
                break;
        }
        setTotal(); //计算单个商品总金额
        setTotalAll(); //计算全部商品总金额
    }
    //推荐尺寸
    window.rSize = function(id, size) {
        var mb = (size / 1024 / 1024).toFixed(2); //获取图片字节转换成mb
        var mbSize = parseFloat(mb);
        var elLi =$("#"+id);
        if (mbSize < config.section_1) {

            layer.msg('图片质量有点低，照片可能不太清晰噢~');

            ChoiceSize(id, 5);
            elLi.find('dd:gt(0)').addClass('errtips');
        } else if (mbSize < config.section_2) {
            ChoiceSize(id, 5);
            elLi.find('dd:gt(0)').addClass('errtips');
        } else if (mbSize > config.section_2 && mbSize < config.section_3) {
            ChoiceSize(id, 5);
            elLi.find("dd:lt(2)").addClass('canclick').end().find('dd:gt(1)').addClass('errtips');
        } else if (mbSize > config.section_3 && mbSize < config.section_4) {
            ChoiceSize(id, 5);
            //ChoiceSize(id, 8);
            elLi.find("dd:lt(3)").addClass('canclick').end().find('dd:gt(2)').addClass('errtips');
        } else if (mbSize > config.section_4 && mbSize < config.section_5) {
            ChoiceSize(id, 5);
            //ChoiceSize(id, 12);
            elLi.find("dd:lt(5)").addClass('canclick');
        } else if (mbSize > config.section_5) {
            ChoiceSize(id, 5);
            //ChoiceSize(id, 18);
            elLi.find("dd:lt(5)").addClass('canclick');
        }
    }


    //var arrFile=[];
    //var count=0;
    //var countUp=0;
    //显示图片
    var arrFiles=[];
    function readFile(el, file, size, res) {
        //var obj=$.extend(res,{size:size});
        //arrFile.push(obj);
        for(var i=0;i<res.length;i++){
            var obj=$.extend(res[i],{size:file[i].size,url:res[i].fileAddress});
            arrFiles.push(obj);
        }
        util.fetchTpl("picture-img","subtpl/picItem.html",{"picList":arrFiles});
        $(".load,.load-text").hide(); //隐藏正在上传图片
        scrollBottom(); //回到底部
        addItem(); //插入一个添加按钮

        //设置默认尺寸
        for(var j=0;j<arrFiles.length;j++){
            rSize("a"+arrFiles[j].id, arrFiles[j].size); //推荐尺寸
        }
    }
    //上传图片
    function upFile(el, file,size) {
        $(el).parents("form").ajaxSubmit({
            url: g_baseUrl + 'uploadMultiplePic', //上传图片http://www.sumaophoto.net/webChat/uploadPic   uploadMultiplePic
            iframe: true,
            dataType: "JSON",
            beforeSubmit: function() {
                $(".load,.load-text").show(); //显示正在上传图片
            },
            success: function(res) {
                //count++;
                if (res&&res.length>=1) {
                    readFile(el, file, size||0, res); //显示图片
                } else {
                    layer.msg('图片上传失败！');
                    $(".load,.load-text").hide(); //隐藏正在上传图片
                }
            },
            error: function(arg1, arg2, ex) {
                //count++;
                layer.msg('此图片上传失败！');
                $(".load,.load-text").hide(); //隐藏正在上传图片
            }
        });
    }

    //检查文件类型
    function checkTypeImage(file){
        for(var i=0;i<file.length;i++){
            if(/image\/\w+/.test(file[i].type)){

            }else{
                return false;
            }
        }
        return true;
    }
    //删除图片
    window.removeImg = function(el) {
        el.parents("li").remove();
        //var nub = 37 - $(".box li").length;
        // $(".box li:last").find(".inner").find('p').text("还可添加" + nub + "张相片"); //更改可添加数量
        // if ($(".box li:last").find("input").val()) {
        //     addItem(); //删除最后一个再次插入一个添加按钮
        // }
        //cunt();
        setTotal(); //计算单个商品总金额
        setTotalAll(); //计算全部商品总金额
    }

    /**
     * 页面加载失败重新加载
     * @param  e [description]
     * @return   [description]
     */
    window.onerror_=function(e){
        var that = e;
        var src=that.src;
        var len=src.split("?").length;
        if(len>8){
            that.src="images/photo.png";
            that.onerror=null;
        }else{
            setTimeout(function(){
                that.src=src+"?a="+new Date().getTime();
                setTimeout(function(){
                    setTotal(); //初始化
                    setTotalAll(); //初始化
                    scrollBottom(); //回到底部
                },200);
            },700);
        }
    }
})();



/**
 * 获取openId
 * @return [description]
 */
function onload() {
    var g_body_page = $("body");
    if (util.getSessionKey("openID")) {
        util.sendAjax({
            url: g_baseUrl + "queryWaresPrice",
            data: {}
        }).done(function(data) {
            g_price = data;
        }).fail(function() {
            layer.alert('请重试', {
                closeBtn: 0
            });
            //window.location.reload();
        })
    } else {
        util.sendAjax({
            url: g_baseUrl + "queryOpenID",
            data: { code: g_code }
        }).done(function(data) {
            util.setSessionKey("openID", data.result ? data.result.openID : "");
            //alert(util.getSessionKey("openID"))
            if (util.getSessionKey("openID") != "") {
                return data;
            } else {
                alert("请重试")
                //window.location.reload();
                //new Toast({ context: $('body'), message: '网络异常' }).show();
            }
        }).done(function() {
            util.sendAjax({
                url: g_baseUrl + "queryWaresPrice",
                data: {}
            }).done(function(data) {
                g_price = data;
            }).fail(function() {
                layer.msg("请重试");
                //window.location.reload();
            });
        }).fail(function() {
            //alert(67)
            layer.msg('网络异常');
            //new Toast({ context: $('body'), message: '网络异常' }).show();
        })
    }
}

//提交订单
var submitOrder = function() {
    var Flg = true;
    $("#btn-submitOrder").on("click", function() {
        //window.location="./confirmOrder.html";return;//测试
        var This = $(this);
        var listArray = "";
        var obj = "";
        var price = 0;
        $(".box li").each(function(index, element) {
            if (!$(this).find(".picture").is(":hidden")) {
                var ThisId = $(this).attr("data-id").replace(/^\s+|\s+| $/g, "");
                var ThisVal = $(this).find(".show-count").text(); //数量
                var ThisSize = $(this).find("dd.curr").attr("data-value"); //尺寸
                if(!ThisId){
                    util.sendAjaxImg($(this).attr("id"));//传递图片到后台
                    layer.msg('等待2秒再试试');
                    return;
                }

                //obj={id:ThisId,nub:ThisVal,size:ThisSize};
                var c = ThisSize+"c";
                price += 100*g_price.goods[c] * parseInt(ThisVal); //计算价格
                obj = ThisId + "," + ThisVal + "," + ThisSize;
                if (listArray) {
                    listArray = listArray + "|" + obj;
                } else {
                    listArray = listArray + obj;
                }

            }
        });
        console.log(listArray); //id ,num,size
        if (listArray.length && Flg) {
            Flg = false;
            This.addClass("curr");
            util.sendAjax({
                url: g_baseUrl + "createOrder",
                data: { "fileIds": listArray, openID: util.getSessionKey("openID"), orderAmt: parseInt(price)}
            }).done(function(data) {
                if (!data||!data.success) {
                    Flg = true;
                    This.removeClass("curr");
                    layer.msg("订单生成失败！请重新操作");
                    return;
                } else {
                    Flg = true;
                    util.setSessionKey("freightAmt",parseInt(g_price.freightAmt*100));
                    window.location.href = "confirmOrder2.html?o=" + data.result; //页面跳转
                }
            }).fail(function() {
                layer.msg("订单生成失败！请重新操作");
                This.removeClass("curr");
            })
        } else {
            alert("请添加图片");
        }
        return false;
    });
}();


//input只能输入数字
$(".isnumber").on("input propertychange", function() {
    var pattern = /^[0-9]*$/;
    if (pattern.test($(this).val()) == false) {
        $(this).val("");
    }
});
//input只能输入带一个小数点数字
$(".isFloating").blur(function() {
    var pattern = /^(\d*\.)?\d+$/;
    if (pattern.test($(this).val()) == false) {
        $(this).val("");
    }
});


//隐藏弹窗
$(".dialog .cancel").on("click", function() {
    $(".dialog,.bj-dialog").hide();
    return false
});

//支付方式
$(".Payment-method").on("click", "a", function() {
    $(this).addClass("curr").siblings().removeClass("curr");
});


//实名认证-选择性别
// $(".sex label").on("click", function() {
//     var This = $(this);
//     This.addClass("curr").find("input").attr("checked", true).end().siblings().removeClass("curr").find("input").attr("checked", false);
//     return false;
// });

//菜单
$(".menu a").on("click", function() {
    if ($(this).hasClass("curr")) {
        $(this).removeClass("curr");
        $("#wrapper").show().siblings(".nav-box").hide();
    } else {
        $(this).addClass("curr");
        $("#wrapper").hide().siblings(".nav-box").show();
    }
});
