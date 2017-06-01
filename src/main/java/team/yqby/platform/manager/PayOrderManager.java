package team.yqby.platform.manager;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.req.PayNotifyReq;
import team.yqby.platform.base.req.WeChatCreateOrder;
import team.yqby.platform.base.res.GoodsRes;
import team.yqby.platform.base.res.PayConfirmRes;
import team.yqby.platform.common.WebCall;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.*;
import team.yqby.platform.common.util.*;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.mapper.TOrderMapper;
import team.yqby.platform.pojo.TFile;
import team.yqby.platform.pojo.TFileExample;
import team.yqby.platform.pojo.TOrder;
import team.yqby.platform.pojo.TOrderExample;
import team.yqby.platform.service.IRedisService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PayOrderManager {
    @Autowired
    private TOrderMapper tOrderMapper;
    @Autowired
    private TFileMapper tFileMapper;
    @Autowired
    private IRedisService redisService;

    /**
     * 支付下单
     *
     * @param openID 用户编号
     * @return
     */
    public String createPayOrder(String openID, String orderAmt, String fileIds) {
        String[] fileIdArr = StringUtils.split(fileIds, "|");
        String businessInfo = "";
        List<String> businessList = new ArrayList<>();
        Long pTotalAmt = 0L;
        Map<String, String> goodsMap = JSON.parseObject(redisService.get(PublicConfig.GOODS_REDIS_KEY), GoodsRes.class).getGoods();
        for (String fileInfo : fileIdArr) {
            String[] files = StringUtils.split(fileInfo, ",");
            Long picId = Long.valueOf(files[0]);
            String picNum = files[1];
            String picSize = files[2];
            String picSinglePrice = goodsMap.get(Joiner.on("").join("c", picSize));
            String picUrl = tFileMapper.selectByPrimaryKey(picId).getFileAddress();
            businessInfo = Joiner.on(",").join(picId, picNum, picSize, picSinglePrice, picUrl);
            businessList.add(businessInfo);
            pTotalAmt += Long.valueOf(picNum) * Long.valueOf(MoneyUtil.changeY2F(picSinglePrice));
        }
        //商品价格校验
        if (!StringUtils.equals(String.valueOf(pTotalAmt), orderAmt)) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20002);
        }
        String orderNo = NumberUtil.getOrderNoRandom();
        TOrder tOrder = new TOrder();
        tOrder.setOrderno(orderNo);
        tOrder.setCustomerId(openID);
        tOrder.setOrderamt(orderAmt);
        tOrder.setCreatetime(new Date());
        tOrder.setPutOrderTime(new Date());
        tOrder.setUpdatetime(new Date());
        tOrder.setIsPay(PayFlagEnum.N.getCode());
        tOrder.setProcess(ProcessEnum.INIT.getCode());
        tOrder.setDeliveryinfo(Joiner.on("@").join(businessList));
        int i = tOrderMapper.insert(tOrder);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003);
        }
        return orderNo;
    }

    /**
     * 更新文件信息
     *
     * @param fileIds
     * @param orderNo
     */
    public void updateFileOrder(String fileIds, String orderNo) {
        TFile tFile = new TFile();
        tFile.setOrderId(orderNo);
        TFileExample tFileExample = new TFileExample();
        String[] fileIdStrings = StringUtils.split(fileIds, "|");
        List<Long> longList = new ArrayList<Long>();
        for (String fs : fileIdStrings) {
            String[] files = StringUtils.split(fs, ",");
            longList.add(Long.valueOf(files[0]));
        }
        tFileExample.createCriteria().andIdIn(longList);
        int i = tFileMapper.updateByExampleSelective(tFile, tFileExample);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003);
        }
    }


    /**
     * 校验订单
     *
     * @param orderAmt
     * @param orderNo
     */
    public void checkOrder(Long orderAmt, Long freightAmt, String orderNo, String openID) {
        String freightAmtStr = JSON.parseObject(redisService.get(PublicConfig.GOODS_REDIS_KEY), GoodsRes.class).getFreightAmt();
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(orderNo).andCustomerIdEqualTo(openID);
        List<TOrder> tOrderList = tOrderMapper.selectByExample(tOrderExample);
        if (tOrderList == null || tOrderList.isEmpty()) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10006);
        }


        if (Long.valueOf(tOrderList.get(0).getOrderamt()).longValue() != orderAmt.longValue()) {
            log.error("订单金额orderAmt被篡改，订单号:{}", orderNo);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20002);
        }
        if (!tOrderList.get(0).getProcess().equals(ProcessEnum.WAIT_PAY.getCode())) {
            if (!StringUtils.equals(MoneyUtil.changeY2F(freightAmtStr), String.valueOf(freightAmt))) {
                log.error("订单金额freightAmt被篡改，订单号:{}", orderNo);
                throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20002);
            }
        }

        if (tOrderList.get(0).getProcess().equals(ProcessEnum.PAY_SUCCESS.getCode())) {
            log.error("订单已支付成功过,订单号:{}", orderNo);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10010);
        }

    }

    /**
     * 更新订单信息
     *
     * @param addressId
     * @param shopId
     * @param orderNo
     */
    public void updateOrderInfo(Long addressId, Long shopId, String orderNo, Long orderAmt, Long freightAmt) {
        freightAmt = freightAmt == null ? 0L : freightAmt;
        TOrder tOrder = new TOrder();
        tOrder.setAddressid(Long.valueOf(addressId));
        tOrder.setShopid(shopId);
        tOrder.setOrderamt(String.valueOf(orderAmt + freightAmt));
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(orderNo);
        int i = tOrderMapper.updateByExampleSelective(tOrder, tOrderExample);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20001);
        }
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
    public WeChatXmlUtil createWeChatOrder(String openId, String orderNo, Long orderAmt, Long freightAmt) {
        try {
            freightAmt = freightAmt == null ? 0L : freightAmt;
            WeChatCreateOrder weChatCreateOrder = new WeChatCreateOrder();
            weChatCreateOrder.setAppid(PublicConfig.APP_ID);
            weChatCreateOrder.setMch_id(PublicConfig.MCH_ID);
            weChatCreateOrder.setBody(PublicConfig.GOODS_NAME);
            weChatCreateOrder.setNonce_str(MD5Util.MD5Encode(Joiner.on("&").join(orderNo, PublicConfig.MCH_KEY)));
            weChatCreateOrder.setNotify_url(PublicConfig.PAY_NOTIFY_URL);
            weChatCreateOrder.setOpenid(openId);
            weChatCreateOrder.setOut_trade_no(orderNo);
            weChatCreateOrder.setSpbill_create_ip(IPUtil.getLocalIP());
            weChatCreateOrder.setTotal_fee(orderAmt + freightAmt);
            weChatCreateOrder.setTrade_type(PublicConfig.TRADE_TYPE);
            weChatCreateOrder.setSign(WeChatXmlUtil.getSign(BeanToMapUtil.convertBean(weChatCreateOrder, ""), PublicConfig.MCH_KEY));
            String requestXml = WeChatXmlUtil.toXml(weChatCreateOrder).replace("__", "_");
            String responseXml = WebCall.xmlSyncSend(PublicConfig.WX_CREATE_ORDER_URL, requestXml);
            WeChatXmlUtil weChatXmlUtil = WeChatXmlUtil.fromXML(responseXml);
            //下单成功
            updateOrderStatus(orderNo, "", ProcessEnum.WAIT_PAY.getCode(), PayFlagEnum.N.getCode(), weChatXmlUtil.getResult_code(), weChatXmlUtil.getReturn_msg(), null);
            return weChatXmlUtil;
        } catch (AutoPlatformException e) {
            //下单失败
            updateOrderStatus(orderNo, "", ProcessEnum.ORDER_FAIL.getCode(), PayFlagEnum.N.getCode(), e.getCode(), e.getMessage(), null);
            log.error("createWeChatOrder AutoPlatformException error,", e);
            throw new AutoPlatformException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            //下单失败
            updateOrderStatus(orderNo, "", ProcessEnum.ORDER_FAIL.getCode(), PayFlagEnum.N.getCode(), ServiceErrorCode.ERROR_CODE_A10003.getResCode(), ServiceErrorCode.ERROR_CODE_A10003.getResDesc(), null);
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
    public PayConfirmRes resultConversion(WeChatXmlUtil weChatXmlUtil) {
        String packageStr = Joiner.on("=").join("prepay_id", weChatXmlUtil.getPrepay_id());
        PayConfirmRes payConfirmRes = new PayConfirmRes();
        payConfirmRes.setAppId(weChatXmlUtil.getAppid());
        payConfirmRes.setTimeStamp(System.currentTimeMillis());
        payConfirmRes.setNonceStr(MD5Util.MD5Encode(Joiner.on("&").join(weChatXmlUtil.getPrepay_id(), PublicConfig.MCH_KEY)));
        payConfirmRes.setPackage_gjz(packageStr);
        payConfirmRes.setSignType(PublicConfig.SIGN_TYPE);
        try {
            payConfirmRes.setPaySign(WeChatXmlUtil.getSign(BeanToMapUtil.convertBean(payConfirmRes, ""), PublicConfig.MCH_KEY));
        } catch (Exception e) {
            log.error("resultConversion exception,error", e);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10003.getResCode(), e.getMessage());
        }
        return payConfirmRes;
    }

    /**
     * 更新交易状态
     *
     * @param orderNo
     */
    public void updateOrderStatus(String orderNo, String prePayId, String processStatus, String isPay, String resCode, String resDesc, String payResTime) {
        TOrder tOrder = new TOrder();
        tOrder.setProcess(processStatus);
        tOrder.setResorderno(prePayId);
        tOrder.setUpdatetime(new Date());
        tOrder.setIsPay(isPay);
        tOrder.setRescode(resCode);
        tOrder.setResdesc(ParamsValidate.strEncode(resDesc));
        if (StringUtils.isNotEmpty(payResTime)) {
            tOrder.setPutOrderTime(DateUtil.parse(payResTime, DateUtil.fullPattern));
        }
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(orderNo);
        int i = tOrderMapper.updateByExampleSelective(tOrder, tOrderExample);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10007.getResCode(), ServiceErrorCode.ERROR_CODE_A10007.getResDesc());
        }
    }

    /**
     * @param payNotifyReq
     */
    public void checkTransSafe(PayNotifyReq payNotifyReq) throws ParseException {
        //1.校验请求参数
        if (payNotifyReq == null) {
            log.error("订单号:{},通知报文有误,对象转换为空", payNotifyReq.getOut_trade_no());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10004);
        }
        //2.校验支付状态
        if (!PublicConfig.CALL_SUCCESS.equals(payNotifyReq.getReturn_code())) {
            updateOrderStatus(payNotifyReq.getOut_trade_no(), payNotifyReq.getTransaction_id(), ProcessEnum.PAY_FAIL.getCode(), PayFlagEnum.Y.getCode(), payNotifyReq.getResult_code(), payNotifyReq.getReturn_msg(), null);
            throw new AutoPlatformException(payNotifyReq.getResult_code(), payNotifyReq.getReturn_msg());
        }
        if (!PublicConfig.CALL_SUCCESS.equals(payNotifyReq.getResult_code())) {
            updateOrderStatus(payNotifyReq.getOut_trade_no(), payNotifyReq.getTransaction_id(), ProcessEnum.PAY_FAIL.getCode(), PayFlagEnum.Y.getCode(), payNotifyReq.getErr_code(), payNotifyReq.getErr_code_des(), null);
            throw new AutoPlatformException(payNotifyReq.getErr_code(), payNotifyReq.getErr_code_des());
        }
        //3.校验SIGN签名
        String sign = WeChatXmlUtil.getSign(BeanToMapUtil.convertBean(payNotifyReq, "sign"), PublicConfig.MCH_KEY);
        if (!sign.equals(payNotifyReq.getSign())) {
            log.error("订单号:{},请求的SIGN:{},生成的SIGN:{}", payNotifyReq.getOut_trade_no(), payNotifyReq.getSign(), sign);
            updateOrderStatus(payNotifyReq.getOut_trade_no(), payNotifyReq.getTransaction_id(), ProcessEnum.PAY_FAIL.getCode(), "Y", ServiceErrorCode.ERROR_CODE_A10005.getResCode(), ServiceErrorCode.ERROR_CODE_A10005.getResDesc(), null);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10005);
        }
    }

    /**
     * 更新支付状态
     *
     * @param payNotifyReq 支付通知信息
     * @return
     */
    public void updatePayResult(PayNotifyReq payNotifyReq) {
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(payNotifyReq.getOut_trade_no());
        List<TOrder> tOrderList = tOrderMapper.selectByExample(tOrderExample);
        if (tOrderList == null || tOrderList.isEmpty()) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10006);
        }
        log.info("订单号：{}，是否已支付：{}，订单状态：{}", payNotifyReq.getOut_trade_no(), tOrderList.get(0).getIsPay(), tOrderList.get(0).getProcess());
        if (tOrderList.get(0).getProcess().equals(ProcessEnum.PAY_SUCCESS.getCode())) {
            log.error("订单已支付成功过,订单号:{}", payNotifyReq.getOut_trade_no());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10010);
        }
        if (tOrderList.get(0).getProcess().equals(ProcessEnum.DELIVERY_SUCCESS.getCode()) || tOrderList.get(0).getProcess().equals(ProcessEnum.DELIVERY_FAIL.getCode())) {
            log.error("订单已发货过,订单号:{}", payNotifyReq.getOut_trade_no());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10011);
        }
        //更新支付结果
        updateOrderStatus(payNotifyReq.getOut_trade_no(), payNotifyReq.getTransaction_id(), ProcessEnum.PAY_SUCCESS.getCode(), PayFlagEnum.N.getCode(), payNotifyReq.getReturn_code(), ProcessEnum.PAY_SUCCESS.getDesc(), payNotifyReq.getTime_end());
    }
}
