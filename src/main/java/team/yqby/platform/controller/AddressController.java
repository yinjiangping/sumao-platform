package team.yqby.platform.controller;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.req.AddressReq;
import team.yqby.platform.base.res.AddressRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.service.DeliveryAddressService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class AddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    /**
     * 查询收获地址
     *
     * @return
     */
    @RequestMapping(value = "/queryAddress")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<AddressRes>> queryAddress(String openID, String queryFlag, HttpServletRequest request) {
        try {
            log.info("queryAddress started, request params:{},{}", openID, queryFlag);
            List<AddressRes> resList = deliveryAddressService.queryAddress(openID, queryFlag);
            log.info("queryAddress finished, userName:{}", openID);
            return new Response<>(resList);
        } catch (AutoPlatformException e) {
            log.error(" queryAddress meet error, userName:{}, response:{}", openID, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" queryAddress meet error, userName:{}, response:{}", openID, Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/delAddress")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean delAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("operaAddress started, request params:{},{}", addressReq.getOpenID());
            deliveryAddressService.operaAddress(addressReq, "del");
            log.info("operaAddress finished, userName:{}", addressReq.getOpenID());
            return true;
        } catch (AutoPlatformException e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return false;
        } catch (Exception e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    /**
     * 添加收获地址
     *
     * @return
     */
    @RequestMapping(value = "/addAddress")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean addAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("operaAddress started, request params:{},{}", addressReq.getOpenID());
            deliveryAddressService.operaAddress(addressReq, "add");
            log.info("operaAddress finished, userName:{}", addressReq.getOpenID());
            return true;
        } catch (AutoPlatformException e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return false;
        } catch (Exception e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return false;
        }
    }

}
