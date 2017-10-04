package team.yqby.platform.controller;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.TUserInfo;
import team.yqby.platform.base.res.OrderDetailRes;
import team.yqby.platform.base.res.OrderRes;
import team.yqby.platform.common.constant.SystemConstant;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.ProcessEnum;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.service.OrderInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 查询订单信息
     *
     * @param openID 查询用户编号
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response<List<OrderRes>> queryOrder(String openID, String orderNo) {
        try {
            log.info("queryOrder started, request params:{},{}", openID);
            List<OrderRes> orderResList = orderInfoService.queryAll(openID, orderNo);
            log.info("queryOrder finished, userName:{}", openID);
            return new Response<>(orderResList);
        } catch (AutoPlatformException e) {
            log.error(" queryOrder meet error, userName:{}, response:{}", openID, Throwables.getStackTraceAsString(e));
            return new Response<>(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(" queryOrder meet error, userName:{}, response:{}", openID, Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }
    }

    /**
     * 删除订单
     * tony
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = ApiUrls.DELETE_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response deleteDeleteOrder(String openID, String orderNo) {
        try {
            log.info("delOrder started, request params:{},{}", openID, orderNo);
            this.orderInfoService.deleteOrder(openID,orderNo);
            return new Response("操作成功");
        } catch (AutoPlatformException e) {
            log.error(" delOrder meet error, orderNo:{}, response:{}", orderNo, Throwables.getStackTraceAsString(e));
            return new Response("100005", "删除失败");
        } catch (Exception e) {
            log.error(" delOrder meet error, orderNo:{}, response:{}", orderNo, Throwables.getStackTraceAsString(e));
            return new Response("100005", "删除失败");
        }
    }

    /**
     * 查询所有订单信息
     *
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_ALL_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderRes> queryAllOrder(String orderNo, String process, String startDate, String endDate, HttpServletRequest request) {
        List<OrderRes> orderResList = new ArrayList<>();
        try {
            log.info("queryAllOrder started, request ");
            TUserInfo tUserInfo = (TUserInfo) request.getSession().getAttribute(SystemConstant.SESSION_USER);
            orderResList = orderInfoService.queryAllOrder(orderNo, process, startDate, endDate, tUserInfo.getShopId());
            log.info("queryAllOrder finished,result:{}", orderResList);
            return orderResList;
        } catch (AutoPlatformException e) {
            log.error(" queryAllOrder meet error, response:{}", Throwables.getStackTraceAsString(e));
            return orderResList;
        } catch (Exception e) {
            log.error(" queryAllOrder meet error, response:{}", Throwables.getStackTraceAsString(e));
            return orderResList;
        }
    }


    /**
     * 查询订单明细
     *
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_DETAIL)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderDetailRes queryOrderDetail(String orderNo, HttpServletRequest request) {
        OrderDetailRes orderDetailRes = new OrderDetailRes();
        try {
            log.info("queryOrderDetail started, request ");
            TUserInfo tUserInfo = (TUserInfo) request.getSession().getAttribute(SystemConstant.SESSION_USER);
            orderDetailRes = orderInfoService.queryOrderDetail(orderNo, tUserInfo.getShopId());
            log.info("queryOrderDetail finished,result:{}", orderDetailRes);
            return orderDetailRes;
        } catch (AutoPlatformException e) {
            log.error(" queryOrderDetail meet error, response:{}", Throwables.getStackTraceAsString(e));
            return orderDetailRes;
        } catch (Exception e) {
            log.error(" queryOrderDetail meet error, response:{}", Throwables.getStackTraceAsString(e));
            return orderDetailRes;
        }
    }

    /**
     * 更新订单信息发货
     *
     * @return
     */
    @RequestMapping(value = ApiUrls.ORDER_DELIVERY)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean orderDelivery(String orderNo, String expressName, String expressNo, HttpServletRequest request) {
        try {
            log.info("orderDelivery started, request ");
            TUserInfo tUserInfo = (TUserInfo) request.getSession().getAttribute(SystemConstant.SESSION_USER);
            boolean updateResult = orderInfoService.updateOrder(orderNo, ProcessEnum.DELIVERY_SUCCESS.getCode(), ParamsValidate.strDecode(expressName), expressNo, tUserInfo.getId(), tUserInfo.getUsername());
            log.info("orderDelivery finished,result:{}", updateResult);
            return updateResult;
        } catch (AutoPlatformException e) {
            log.error(" orderDelivery meet error, response:{}", Throwables.getStackTraceAsString(e));
            return false;
        } catch (Exception e) {
            log.error(" orderDelivery meet error, response:{}", Throwables.getStackTraceAsString(e));
            return false;
        }
    }

}
