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
import team.yqby.platform.base.req.PayOrderReq;
import team.yqby.platform.base.res.PayOrderRes;
import team.yqby.platform.base.res.PaySignRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.ErrorCodeEnum;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.manager.PayOrderManager;
import team.yqby.platform.service.FlowTicketService;
import team.yqby.platform.service.PayOrderService;

import javax.validation.Valid;

/**
 * ticket业务
 * Author: luwanchuan
 * Date: 2017/1/8
 */
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
    @RequestMapping(value = ApiUrls.FLOW_CREATE_ORDER_URL, method = RequestMethod.POST)
    public
    @ResponseBody
    Response<PayOrderRes> createOrder(@Valid @RequestBody PayOrderReq payOrderReq, Errors errors) {
        PayOrderRes payOrderRes = null;
        try {
            log.info("createOrder started, request params:{}", payOrderReq);

            //1.校验请求参数
            ParamsValidate.validParamError(errors);

            //2.生成支付订单
            payOrderRes = payOrderService.createOrder(payOrderReq);

            log.info("createOrder finished, openId:{}, response:{}", payOrderReq.getOpenID(), payOrderRes);
        } catch (AutoPlatformException e) {
            log.error("createOrder meet error, openId:{}, response:{}", payOrderReq.getOpenID(), e);
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("createOrder meet error, openId:{}, response:{}", payOrderReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
        return new Response<>(payOrderRes);
    }

}
