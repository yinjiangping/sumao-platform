package team.yqby.platform.manager;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.req.WeChatCreateOrder;
import team.yqby.platform.base.res.PayOrderRes;
import team.yqby.platform.common.WebCall;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.*;
import team.yqby.platform.common.util.*;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TOrderMapper;
import team.yqby.platform.pojo.TOrder;
import team.yqby.platform.pojo.TOrderExample;

import java.util.Date;

@Service
@Slf4j
public class PayOrderManager {
    @Autowired
    private TOrderMapper tOrderMapper;

    /**
     * 支付下单
     *
     * @param openID    用户编号
     * @return
     */
    public String createPayOrder(String openID,String orderAmt) {
        String orderNo = NumberUtil.getOrderNoRandom();
        TOrder tOrder = new TOrder();
        tOrder.setOrderno(orderNo);
        tOrder.setCustomerId(openID);
        tOrder.setOrderamt(orderAmt);
        tOrder.setCreatetime(new Date());
        tOrder.setUpdatetime(new Date());
        tOrder.setIsPay(Byte.valueOf(PayFlagEnum.STR_0.getCode()));
        tOrder.setProcess(Byte.valueOf(ProcessEnum.INIT.getCode()));
        int i = tOrderMapper.insert(tOrder);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003);
        }
        return orderNo;
    }

    /**
     * 微信下单
     *
     * @param openId   用户编号
     * @param orderNo  订单号
     * @param orderAmt 订单金额
     * @return
     * @throws AutoPlatformException
     * @throws Exception
     */
    public WeChatXmlUtil createWeChatOrder(String openId, String orderNo, Long orderAmt) {
        try {
            WeChatCreateOrder weChatCreateOrder = new WeChatCreateOrder();
            weChatCreateOrder.setAppid(PublicConfig.APP_ID);
            weChatCreateOrder.setMch_id(PublicConfig.MCH_ID);
            weChatCreateOrder.setBody(PublicConfig.GOODS_NAME);
            weChatCreateOrder.setNonce_str(MD5Util.MD5Encode(Joiner.on("&").join(orderNo, PublicConfig.MCH_KEY)));
            weChatCreateOrder.setNotify_url(PublicConfig.PAY_NOTIFY_URL);
            weChatCreateOrder.setOpenid(openId);
            weChatCreateOrder.setOut_trade_no(orderNo);
            weChatCreateOrder.setSpbill_create_ip(IPUtil.getLocalIP());
            weChatCreateOrder.setTotal_fee(orderAmt);
            weChatCreateOrder.setTrade_type(PublicConfig.TRADE_TYPE);
            weChatCreateOrder.setSign(WeChatXmlUtil.getSign(BeanToMapUtil.convertBean(weChatCreateOrder, ""), PublicConfig.MCH_KEY));
            String requestXml = WeChatXmlUtil.toXml(weChatCreateOrder).replace("__", "_");
            String responseXml = WebCall.xmlSyncSend(PublicConfig.WX_CREATE_ORDER_URL, requestXml);
            WeChatXmlUtil weChatXmlUtil = WeChatXmlUtil.fromXML(responseXml);
            //下单成功
            updateOrderStatus(orderNo, "", TransStatusEnum.WAIT_PAY.getStatus(), "0");
            return weChatXmlUtil;
        } catch (AutoPlatformException e) {
            //下单失败
            updateOrderStatus(orderNo, "", ProcessEnum.ORDER_FAIL.getCode(),"0");
            log.error("createWeChatOrder AutoPlatformException error,", e);
            throw new AutoPlatformException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            //下单失败
            updateOrderStatus(orderNo, "", ProcessEnum.ORDER_FAIL.getCode(),"0");
            log.error("createWeChatOrder AutoPlatformException error,", e);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003.getResCode(), e.getMessage());
        }
    }

    /**
     * 下单结果转换
     *
     * @param weChatXmlUtil
     * @return
     */
    public PayOrderRes resultConversion(WeChatXmlUtil weChatXmlUtil) {
        String packageStr = Joiner.on("=").join("prepay_id", weChatXmlUtil.getPrepay_id());
        PayOrderRes payOrderRes = new PayOrderRes();
        payOrderRes.setAppId(weChatXmlUtil.getAppid());
        payOrderRes.setTimeStamp(System.currentTimeMillis());
        payOrderRes.setNonceStr(MD5Util.MD5Encode(Joiner.on("&").join(weChatXmlUtil.getPrepay_id(), PublicConfig.MCH_KEY)));
        payOrderRes.setPackage_gjz(packageStr);
        payOrderRes.setSignType(PublicConfig.SIGN_TYPE);
        try {
            payOrderRes.setPaySign(WeChatXmlUtil.getSign(BeanToMapUtil.convertBean(payOrderRes, ""), PublicConfig.MCH_KEY));
        } catch (Exception e) {
            log.error("resultConversion exception,error", e);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003.getResCode(), e.getMessage());
        }
        return payOrderRes;
    }

    /**
     * 更新交易状态
     *
     * @param orderNo
     */
    public void updateOrderStatus(String orderNo, String prePayId, String processStatus,String isPay) {
        TOrder tOrder = new TOrder();
        tOrder.setProcess(Byte.valueOf(processStatus));
        tOrder.setResorderno(prePayId);
        tOrder.setUpdatetime(new Date());
        tOrder.setIsPay(Byte.valueOf(isPay));
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(orderNo);
        int i = tOrderMapper.updateByExample(tOrder, tOrderExample);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10007.getResCode(), ServiceErrorCode.ERROR_CODE_A10007.getResDesc());
        }
    }
}
