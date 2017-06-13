package team.yqby.platform.service;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.res.FlowOpenIDRes;
import team.yqby.platform.base.res.PaySignRes;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.manager.TicketManager;

/**
 * Date: 2017/1/8
 */
@Service
@Slf4j
public class TicketService {
    @Autowired
    private IRedisService iRedisService;

    @Autowired
    private TicketManager ticketManager;

    /**
     * 根据code获取openID
     *
     * @param code
     * @return
     */
    public Response<String> queryOpenIDByCode(String code) {
        Response response;
        Response<FlowOpenIDRes> resResponse = ticketManager.queryByCode(code);
        if (resResponse.isSuccess()) {
            String openId = resResponse.getResult().getOpenid();
            iRedisService.set(openId, openId, 60 * 60 * 2L);
            response = new Response(openId);
        } else {
            response = new Response(resResponse.getErrorCode(), resResponse.getErrorMsg());
        }

        return response;
    }

    /**
     * 根据openId获取jsApiTicket加密
     *
     * @param openId
     * @return
     */
    public PaySignRes queryJsApiTicketEnc(String openId, String url) {

        //1.校验openId是否存在
        ticketManager.checkOpenIdIsExpires(iRedisService.get(openId), openId);

        //2.获取access_token
        String accessToken = ticketManager.queryAccessToken();

        //3.获取jsApiTicket
        String jsApiTicket = ticketManager.queryJssApiTicket(accessToken);

        //4.验证参数签名
        PaySignRes paySignRes = ticketManager.getPaySignRes(jsApiTicket, url);

        return paySignRes;
    }

    /**
     * 获取上传授权信息
     *
     * @return
     */
    public String getUploadToken() {
        Auth auth = Auth.create(PublicConfig.ACCESS_KEY, PublicConfig.SECRET_KEY);
        String uploadToken = auth.uploadToken(PublicConfig.BUCKET_NAME);
        iRedisService.set(uploadToken.split(":")[1], uploadToken.split(":")[1], 60 * 20L);
        return uploadToken;
    }

}
