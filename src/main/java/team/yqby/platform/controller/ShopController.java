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
import team.yqby.platform.pojo.TShop;
import team.yqby.platform.service.DeliveryAddressService;
import team.yqby.platform.service.ShopInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class ShopController {
    @Autowired
    private ShopInfoService shopInfoService;

    /**
     * 查询门店信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_SHOP)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<TShop>> queryShop(HttpServletRequest request) {
        try {
            log.info("queryShop started, request");
            List<TShop> tShopList = shopInfoService.queryAll();
            log.info("queryShop finished, result:{}", tShopList);
            return new Response<>(tShopList);
        } catch (AutoPlatformException e) {
            log.error(" queryShop meet error,  response:{}", Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" queryShop meet error,  response:{}", Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }

}
