package team.yqby.platform.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.req.FlowOpenIDDto;
import team.yqby.platform.base.res.PaySignRes;
import team.yqby.platform.base.res.UploadTokenRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.ErrorCodeEnum;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.service.TicketService;


@Slf4j
@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * @param code
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_OPEN_ID, method = RequestMethod.POST)
    @ResponseBody
    public Response<FlowOpenIDDto> queryByCode(String code) {
        Response response;
        Response<String> stringResponse = ticketService.queryOpenIDByCode(code);
        if (stringResponse.isSuccess()) {
            FlowOpenIDDto flowOpenIDDto = new FlowOpenIDDto();
            flowOpenIDDto.setOpenID(stringResponse.getResult());
            response = new Response(flowOpenIDDto);
        } else {
            response = new Response(ErrorCodeEnum.SYSTEM_ERROR.getCode(),
                    ErrorCodeEnum.SYSTEM_ERROR.getDesc());
        }
        return response;
    }

    /**
     * @param openID
     * @return
     */
    @RequestMapping(value = ApiUrls.PAY_SIGN)
    @ResponseBody
    public Response<PaySignRes> paySign(String openID, String url) {
        try {
            PaySignRes paySignRes = ticketService.queryJsApiTicketEnc(openID, url);
            if (StringUtils.isEmpty(paySignRes.getSignature())) {
                return new Response<>(ServiceErrorCode.ERROR_CODE_A10008);
            }
            return new Response<>(paySignRes);
        } catch (Exception e) {
            log.error("paySign exception,error", e);
            return new Response<>(ServiceErrorCode.ERROR_CODE_A10009);
        }
    }

    @RequestMapping(value = ApiUrls.GET_UPLOAD_TOKEN)
    @ResponseBody
    public UploadTokenRes makeToken() {
        UploadTokenRes uploadTokenRes = new UploadTokenRes();
        try {
           String token = ticketService.getUploadToken();
            uploadTokenRes.setUptoken(token);
            return uploadTokenRes;
        } catch (Exception e) {
            log.error("makeToken exception,error", e);
            return uploadTokenRes;
        }
    }

}
