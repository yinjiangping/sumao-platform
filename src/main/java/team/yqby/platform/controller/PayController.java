package team.yqby.platform.controller;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.req.FlowOpenIDDto;
import team.yqby.platform.base.req.PayConfirmReq;
import team.yqby.platform.base.req.PayNotifyReq;
import team.yqby.platform.base.req.PayOrderReq;
import team.yqby.platform.base.res.PayConfirmRes;
import team.yqby.platform.base.res.PayNotifyRes;
import team.yqby.platform.base.res.PayOrderRes;
import team.yqby.platform.base.res.PaySignRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.ErrorCodeEnum;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.common.util.StreamUtil;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.manager.PayOrderManager;
import team.yqby.platform.service.FlowTicketService;
import team.yqby.platform.service.PayOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
public class PayController {
    @Autowired
    private PayOrderService payOrderService;
    /**
     * 支付下单
     *
     * @param payOrderReq 支付下单对象
     * @param errors       绑定对象参数错误
     * @return 下单结果
     */
    @RequestMapping(value = ApiUrls.CREATE_ORDER_URL, method = RequestMethod.POST)
    public
    @ResponseBody
    Response<String> createOrder(@Valid @RequestBody PayOrderReq payOrderReq, Errors errors) {
        try {
            log.info("createOrder started, request params:{}", payOrderReq);

            //1.校验请求参数
            ParamsValidate.validParamError(errors);

            //2.生成支付订单
            String result = payOrderService.createOrder(payOrderReq);

            log.info("createOrder finished, openId:{}, response:{}", payOrderReq.getOpenID(), result);
            return new Response<>(result);
        } catch (AutoPlatformException e) {
            log.error("createOrder meet error, openId:{}, response:{}", payOrderReq.getOpenID(), e);
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("createOrder meet error, openId:{}, response:{}", payOrderReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }

    }


    /**
     * 确认订单
     *
     * @param payConfirmReq 确认订单请求参数
     * @param errors       绑定对象参数错误
     * @return 确认订单结果
     */
    @RequestMapping(value = ApiUrls.CONFIRM_ORDER_URL, method = RequestMethod.POST)
    public
    @ResponseBody
    Response<PayConfirmRes> confirmOrder(@Valid @RequestBody PayConfirmReq payConfirmReq, Errors errors) {
        PayConfirmRes payConfirmRes = null;
        try {
            log.info("confirmOrder started, request params:{}", payConfirmReq);

            //1.校验请求参数
            ParamsValidate.validParamError(errors);

            //2.确认订单
            payConfirmRes = payOrderService.confirmOrder(payConfirmReq);

            log.info("confirmOrder finished, openId:{}, response:{}", payConfirmReq.getOpenID(), payConfirmRes);
        } catch (AutoPlatformException e) {
            log.error("confirmOrder meet error, openId:{}, response:{}", payConfirmReq.getOpenID(), e);
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("confirmOrder meet error, openId:{}, response:{}", payConfirmReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
        return new Response<>(payConfirmRes);
    }

    /**
     * 支付结果通知
     *
     * @param request 请求参数(XML报文)
     * @return
     */
    @RequestMapping(value = ApiUrls.PAY_NOTIFY_URL)
    public
    @ResponseBody
    PayNotifyRes payCallBack(HttpServletRequest request) {
        try {
            String requestXml = StreamUtil.streamToStr(request);
            log.info("payCallBack started, request params:{}", requestXml);

            //1.XML请求报文转对象
            PayNotifyReq payNotifyReq = PayNotifyReq.fromXML(requestXml);

            //2.通知结果处理(更新支付结果并充值)
            PayNotifyRes payNotifyRes = payOrderService.payNotify(payNotifyReq);

            log.info("payCallBack finished, payNotifyReq:{}, response:{}", payNotifyRes);
            return payNotifyRes;
        } catch (AutoPlatformException e) {
            log.error("payCallBack meet error, ", e);
            return new PayNotifyRes(PublicConfig.CALL_SUCCESS, PublicConfig.OK);
        } catch (Exception e) {
            log.error("payCallBack meet error, ", Throwables.getStackTraceAsString(e));
            return new PayNotifyRes(ServiceErrorCode.ERROR_CODE_F99999.getResCode(), ServiceErrorCode.ERROR_CODE_F99999.getResDesc());
        }
    }

}
