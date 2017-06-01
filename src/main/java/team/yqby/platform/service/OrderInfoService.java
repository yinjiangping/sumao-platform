package team.yqby.platform.service;


import team.yqby.platform.base.res.OrderDetailRes;
import team.yqby.platform.base.res.OrderRes;

import java.util.List;

public interface OrderInfoService {

    public List<OrderRes> queryAll(String openID,String orderNo);

    public List<OrderRes> queryAllOrder(String orderNo, String process, String startDate, String endDate,Long shopId);

    public OrderDetailRes queryOrderDetail(String orderNo,Long shopId);

    public boolean updateOrder(String orderNo, String process,String expressName,String expressNo,Long userId,String userName);

}
