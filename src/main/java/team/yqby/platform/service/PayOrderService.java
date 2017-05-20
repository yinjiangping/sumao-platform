package team.yqby.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.req.PayOrderReq;
import team.yqby.platform.base.res.PayOrderRes;
import team.yqby.platform.common.util.WeChatXmlUtil;
import team.yqby.platform.manager.PayOrderManager;

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
    public PayOrderRes createOrder(PayOrderReq payOrderReq) {

        //1.支付下单
        String orderNo = payOrderManager.createPayOrder( payOrderReq.getOpenID(),String.valueOf(payOrderReq.getOrderAmt()));

        //3.微信下单
        WeChatXmlUtil weChatXmlUtil = payOrderManager.createWeChatOrder(payOrderReq.getOpenID(), orderNo, payOrderReq.getOrderAmt());

        //4.返回结果转换
        PayOrderRes payOrderRes = payOrderManager.resultConversion(weChatXmlUtil);

        return payOrderRes;
    }
}
