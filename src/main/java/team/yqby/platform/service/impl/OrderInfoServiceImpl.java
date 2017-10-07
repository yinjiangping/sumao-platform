package team.yqby.platform.service.impl;


import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.base.res.ImagesRes;
import team.yqby.platform.base.res.OrderDetailRes;
import team.yqby.platform.base.res.OrderRes;
import team.yqby.platform.common.enums.ProcessEnum;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.common.util.MoneyUtil;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.mapper.TDeliveryAddressMapper;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.mapper.TOrderMapper;
import team.yqby.platform.mapper.TShopMapper;
import team.yqby.platform.pojo.*;
import team.yqby.platform.service.OrderInfoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private TOrderMapper tOrderMapper;
    @Autowired
    private TDeliveryAddressMapper tDeliveryAddressMapper;
    @Autowired
    private TShopMapper tShopMapper;
    @Autowired
    private TFileMapper tFileMapper;

    @Override
    public List<OrderRes> queryAll(String openID, String orderNo) {
        TOrderExample tOrderExample = new TOrderExample();
        TOrderExample.Criteria criteria = tOrderExample.createCriteria();
        criteria.andCustomerIdEqualTo(openID);
        if (StringUtils.isNotEmpty(orderNo)) {
            criteria.andOrdernoEqualTo(orderNo);
        }
        List<TOrder> tOrderList = tOrderMapper.selectByExample(tOrderExample);
        List<OrderRes> orderResList = new ArrayList<>();
        for (TOrder tOrder : tOrderList) {
            OrderRes orderRes = new OrderRes();
            orderRes.setOrderNo(tOrder.getOrderno());
            orderRes.setPrice(tOrder.getOrderamt());
            orderRes.setFreightAmt(tOrder.getFreightamt());
            orderRes.setDeliverType(String.valueOf(tOrder.getDelivertype()));
            orderRes.setState(tOrder.getProcess());
            TFileExample tFileExample = new TFileExample();
            tFileExample.createCriteria().andOrderIdEqualTo(tOrder.getOrderno());
            List<TFile> tFiles = tFileMapper.selectByExample(tFileExample);
            List<ImagesRes> imagesResList = new ArrayList<>();
            for (TFile tFile : tFiles) {
                ImagesRes imagesRes = new ImagesRes();
                imagesRes.setId(tFile.getId());
                imagesRes.setNumber(String.valueOf(tFile.getFileNum()));
                imagesRes.setPicSize(String.valueOf(tFile.getFileSize()));
                imagesRes.setPrice(MoneyUtil.changeF2Y(tFile.getSinglePrice()));
                imagesRes.setImg(tFile.getFileAddress());
                imagesResList.add(imagesRes);
                orderRes.setImages(imagesResList);
            }
            if (StringUtils.isNotEmpty(tOrder.getDeliveryinfo())) {
                String[] strs = StringUtils.split(tOrder.getDeliveryinfo(), "#");
                orderRes.setAddressId(tOrder.getAddressid());
                orderRes.setAddress(ParamsValidate.strDecode(strs[0]));
            }
            orderRes.setShopId(tOrder.getShopid());
            orderRes.setCreateTime(DateUtil.format(tOrder.getCreatetime(), DateUtil.settlePattern));
            orderResList.add(orderRes);
        }
        return orderResList;
    }

    @Override
    public List<OrderRes> queryAllOrder(String orderNo, String process, String startDate, String endDate, Long shopId) {
        TOrderExample tOrderExample = new TOrderExample();
        TOrderExample.Criteria criteria = tOrderExample.createCriteria();
        if (shopId != null && shopId > 0L) {
            criteria.andShopidEqualTo(shopId);
        }
        if (StringUtils.isNotEmpty(orderNo)) {
            criteria.andOrdernoEqualTo(orderNo);
        }
        if (StringUtils.isNotEmpty(process)) {
            criteria.andProcessEqualTo(process);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            criteria.andPutOrderTimeGreaterThanOrEqualTo(DateUtil.parse(startDate + " 00:00:00", DateUtil.settlePattern));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            criteria.andPutOrderTimeLessThanOrEqualTo(DateUtil.parse(endDate + " 23:59:59", DateUtil.settlePattern));
        }
        List<TOrder> tOrderList = tOrderMapper.selectByExample(tOrderExample);
        List<OrderRes> orderResList = new ArrayList<>();
        for (TOrder tOrder : tOrderList) {
            OrderRes orderRes = new OrderRes();
            orderRes.setOrderNo(tOrder.getOrderno());
            orderRes.setPrice(MoneyUtil.changeF2Y(tOrder.getOrderamt()));
            orderRes.setFreightAmt(MoneyUtil.changeF2Y(tOrder.getFreightamt()));
            orderRes.setDeliverType(String.valueOf(tOrder.getDelivertype()));
            orderRes.setState(tOrder.getProcess());
            TFileExample tFileExample = new TFileExample();
            tFileExample.createCriteria().andOrderIdEqualTo(tOrder.getOrderno());
            orderRes.setShopId(tOrder.getShopid());
            orderRes.setCreateTime(DateUtil.format(tOrder.getCreatetime(), DateUtil.settlePattern));
            orderRes.setPutOrderTime(DateUtil.format(tOrder.getPutOrderTime(), DateUtil.settlePattern));
            orderResList.add(orderRes);
        }
        return orderResList;
    }

    @Override
    public OrderDetailRes queryOrderDetail(String orderNo, Long shopId) {
        TOrderExample tOrderExample = new TOrderExample();
        TOrderExample.Criteria criteria = tOrderExample.createCriteria();
        criteria.andOrdernoEqualTo(orderNo);
        if (shopId != null && shopId > 0L) {
            criteria.andShopidEqualTo(shopId);
        }
        TOrder tOrder = tOrderMapper.selectByExample(tOrderExample).get(0);
        OrderDetailRes orderDetailRes = new OrderDetailRes();
        orderDetailRes.setOrderNo(orderNo);
        orderDetailRes.setOpenID(tOrder.getCustomerId());
        orderDetailRes.setOrderAmt(MoneyUtil.changeF2Y(tOrder.getOrderamt()));
        orderDetailRes.setFreightAmt(MoneyUtil.changeF2Y(tOrder.getFreightamt()));
        orderDetailRes.setDeliverType(String.valueOf(tOrder.getDelivertype()));
        orderDetailRes.setPutOrderTime(DateUtil.format(tOrder.getPutOrderTime(), DateUtil.settlePattern));
        orderDetailRes.setState(ProcessEnum.getOrderStatus(tOrder.getProcess()));
        if (!tOrder.getProcess().equals(ProcessEnum.INIT.getCode())) {
            if (StringUtils.isNotEmpty(tOrder.getDeliveryinfo())) {
                String[] strs = StringUtils.split(tOrder.getDeliveryinfo(), "#");
                orderDetailRes.setReceiveAddress(ParamsValidate.strDecode(strs[0]));
                orderDetailRes.setReceiveName(ParamsValidate.strDecode(strs[1]));
                orderDetailRes.setReceivePhone(strs[2]);
            }
            TShop tShop = tShopMapper.selectByPrimaryKey(tOrder.getShopid());
            if (tShop != null) {
                orderDetailRes.setSendAddress(ParamsValidate.strDecode(tShop.getShopAddress()));
                orderDetailRes.setSendName(ParamsValidate.strDecode(tShop.getShopName()));
                orderDetailRes.setSendPhone(tShop.getShopPhone());
            }
        }

        TFileExample tFileExample = new TFileExample();
        tFileExample.createCriteria().andOrderIdEqualTo(tOrder.getOrderno());
        List<TFile> tFiles = tFileMapper.selectByExample(tFileExample);
        List<ImagesRes> imagesResList = new ArrayList<>();
        for (TFile tFile : tFiles) {
            ImagesRes imagesRes = new ImagesRes();
            imagesRes.setId(tFile.getId());
            imagesRes.setNumber(String.valueOf(tFile.getFileNum()));
            imagesRes.setPicSize(String.valueOf(tFile.getFileSize()));
            imagesRes.setPrice(MoneyUtil.changeF2Y(tFile.getSinglePrice()));
            imagesRes.setImg(tFile.getFileAddress());
            imagesResList.add(imagesRes);
            orderDetailRes.setImagesResList(imagesResList);
        }
        orderDetailRes.setExpressInfo(ParamsValidate.strDecode(tOrder.getRemarks()));
        orderDetailRes.setResCode(tOrder.getRescode());
        orderDetailRes.setResDesc(ParamsValidate.strDecode(tOrder.getResdesc()));
        return orderDetailRes;
    }

    @Override
    public boolean updateOrder(String orderNo, String process, String expressName, String expressNo, Long userId, String userName) {
        TOrder tOrder = new TOrder();
        tOrder.setRemarks(ParamsValidate.strEncode(Joiner.on(":").join(expressName, expressNo)));
        tOrder.setProcess(process);
        tOrder.setUpdatetime(new Date());
        tOrder.setUpdateby(userId);
        tOrder.setUpdatebyname(userName);
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andOrdernoEqualTo(orderNo);
        int i = tOrderMapper.updateByExampleSelective(tOrder, tOrderExample);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteOrder(String openID, String orderNo) {
        TOrderExample tOrderExample = new TOrderExample();
        tOrderExample.createCriteria().andCustomerIdEqualTo(openID).andOrdernoEqualTo(orderNo);
        this.tOrderMapper.deleteByExample(tOrderExample);
    }

}
