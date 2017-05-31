package team.yqby.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.req.PayConfirmReq;
import team.yqby.platform.base.req.PayNotifyReq;
import team.yqby.platform.base.req.PayOrderReq;
import team.yqby.platform.base.res.PayConfirmRes;
import team.yqby.platform.base.res.PayNotifyRes;
import team.yqby.platform.base.res.PayOrderRes;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.common.util.WeChatXmlUtil;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.manager.PayOrderManager;

import java.text.ParseException;

@Service
public class PayOrderService {
    @Autowired
    private PayOrderManager payOrderManager;

    /**
     * 支付下单
     *
     * @param payOrderReq 下单请求对象
     * @return
     */
    public String createOrder(PayOrderReq payOrderReq) {

        //1.支付下单
        String orderNo = payOrderManager.createPayOrder(payOrderReq.getOpenID(), String.valueOf(payOrderReq.getOrderAmt()), payOrderReq.getFileIds());

        //2.更新文件表
        payOrderManager.updateFileOrder(payOrderReq.getFileIds(), orderNo);


        return orderNo;
    }

    /**
     * 确认订单
     *
     * @param confirmReq 下单请求对象
     * @return
     */
    public PayConfirmRes confirmOrder(PayConfirmReq confirmReq) {
        //1.检查订单(用户与订单关联，下单金额与确认金额)
        payOrderManager.checkOrder(confirmReq.getOrderAmt(),confirmReq.getFreightAmt(), confirmReq.getOrderNo(), confirmReq.getOpenID());

        //2.更新收获地址&门店信息
        payOrderManager.updateOrderInfo(confirmReq.getAddressId(), confirmReq.getShopId(), confirmReq.getOrderNo(),confirmReq.getOrderAmt(),confirmReq.getFreightAmt());

        //3.微信下单
        WeChatXmlUtil weChatXmlUtil = payOrderManager.createWeChatOrder(confirmReq.getOpenID(), confirmReq.getOrderNo(), confirmReq.getOrderAmt());

        //4.返回结果转换
        PayConfirmRes payConfirmRes = payOrderManager.resultConversion(weChatXmlUtil);

        return payConfirmRes;
    }

    /**
     * 支付回调通知
     *
     * @param payNotifyReq
     * @return
     */
    public PayNotifyRes payNotify(PayNotifyReq payNotifyReq) throws ParseException {

        //1.校验支付状态更新支付结果
        payOrderManager.checkTransSafe(payNotifyReq);

        //2.查询支付订单结果并更新
        payOrderManager.updatePayResult(payNotifyReq);

        return new PayNotifyRes(PublicConfig.CALL_SUCCESS, PublicConfig.OK);
    }
}
