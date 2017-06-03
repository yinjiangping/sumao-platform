package team.yqby.platform.controller;

import com.qiniu.common.Config;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.req.FlowOpenIDDto;
import team.yqby.platform.base.res.PaySignRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.ErrorCodeEnum;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.service.FlowTicketService;

import javax.crypto.Mac;

@Slf4j
@Controller
public class FlowTicketController {

    @Autowired
    private FlowTicketService flowTicketService;

    /**
     * @param code
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_OPEN_ID, method = RequestMethod.POST)
    @ResponseBody
    public Response<FlowOpenIDDto> queryByCode(String code) {
        Response response;
        Response<String> stringResponse = flowTicketService.queryOpenIDByCode(code);
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
            PaySignRes paySignRes = flowTicketService.queryJsApiTicketEnc(openID, url);
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
    public Response<String> makeToken() {
        try {
            Auth auth = Auth.create(PublicConfig.ACCESS_KEY, PublicConfig.SECRET_KEY);
            return new Response(auth.uploadToken(PublicConfig.BUCKET_NAME));
        } catch (Exception e) {
            log.error("makeToken exception,error", e);
            return new Response<>(ServiceErrorCode.ERROR_CODE_A10009);
        }
    }


}
