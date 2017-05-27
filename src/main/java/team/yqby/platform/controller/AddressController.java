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
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.service.DeliveryAddressService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class AddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    /***
     *  查询收获地址
     * @param openID    查询用户编号
     * @param queryFlag  查询标识   all：所有 default：默认收获地址  no_default：非默认收获地址
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_ADDRESS)
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
     *  删除收获地址
     * @param addressReq  收获地址编号
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.DEL_ADDRESS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Long> delAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("delAddress started, request params:{},{}", addressReq.getOpenID());
            Long addressId = deliveryAddressService.operaAddress(addressReq, "del");
            log.info("delAddress finished, userName:{}", addressReq.getOpenID());
            return new Response<>(addressId);
        } catch (AutoPlatformException e) {
            log.error(" delAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" delAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }

    /**
     *  添加收获地址
     * @param addressReq  收获地址编号
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.ADD_ADDRESS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Long> addAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("operaAddress started, request params:{},{}", addressReq.getOpenID());
            Long addressId = deliveryAddressService.operaAddress(addressReq, "add");
            log.info("operaAddress finished, userName:{}", addressReq.getOpenID());
            return new Response<>(addressId);
        } catch (AutoPlatformException e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" operaAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }


    /**
     *  修改收获地址
     * @param addressReq  收获地址编号
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.EDIT_ADDRESS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Long> editAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("editAddress started, request params:{},{}", addressReq.getOpenID());
            Long addressId = deliveryAddressService.operaAddress(addressReq, "edit");
            log.info("editAddress finished, userName:{}", addressReq.getOpenID());
            return new Response<>(addressId);
        } catch (AutoPlatformException e) {
            log.error(" editAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" editAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }
    /**
     *  设置默认
     * @param addressReq  收获地址编号
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.SET_DEFAULT_ADDRESS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<Long> setDefaultAddress(AddressReq addressReq, HttpServletRequest request) {
        try {
            log.info("editAddress started, request params:{},{}", addressReq.getOpenID());
            Long addressId = deliveryAddressService.operaAddress(addressReq, "setDefault");
            log.info("editAddress finished, userName:{}", addressReq.getOpenID());
            return new Response<>(addressId);
        } catch (AutoPlatformException e) {
            log.error(" editAddress meet error, userName:{}, response:{}", addressReq, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" editAddress meet error, userName:{}, response:{}", addressReq.getOpenID(), Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }

}
