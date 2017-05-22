package team.yqby.platform.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.yqby.platform.base.req.AddressReq;
import team.yqby.platform.base.res.AddressRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TDeliveryAddressMapper;
import team.yqby.platform.pojo.TDeliveryAddress;
import team.yqby.platform.pojo.TDeliveryAddressExample;
import team.yqby.platform.service.DeliveryAddressService;

import java.util.ArrayList;
import java.util.List;

@Service("deliveryAddressService")
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    @Autowired
    private TDeliveryAddressMapper tDeliveryAddressMapper;

    @Override
    public List<AddressRes> queryAddress(String openID, String queryFlag) {
        TDeliveryAddressExample tDeliveryAddressExample = new TDeliveryAddressExample();
        TDeliveryAddressExample.Criteria criteria = tDeliveryAddressExample.createCriteria();
        criteria.andCustomerIdEqualTo(openID);
        switch (queryFlag) {
            case "all":
                break;
            case "default":
                criteria.andIsDefaultEqualTo(queryFlag);
                break;
            case "no_default":
                criteria.andIsDefaultEqualTo(queryFlag);
                break;
        }
        List<TDeliveryAddress> deliveryAddressList = tDeliveryAddressMapper.selectByExample(tDeliveryAddressExample);
        List<AddressRes> addressResList = new ArrayList<>();
        for (TDeliveryAddress tDeliveryAddress : deliveryAddressList) {
            AddressRes addressRes = new AddressRes();
            addressRes.setRAddress(tDeliveryAddress.getDeliveryAddress());
            addressRes.setRUserName(tDeliveryAddress.getDeliveryName());
            addressRes.setRPhone(tDeliveryAddress.getDeliveryTel());
            addressRes.setZipCode(tDeliveryAddress.getMailNumber());
            addressRes.setIsDefault(tDeliveryAddress.getIsDefault());
            addressResList.add(addressRes);
        }
        return addressResList;
    }


    @Override
    public void operaAddress(AddressReq addressReq, String operaFlag) {
        int i = 0;
        switch (operaFlag) {
            case "del":
                TDeliveryAddress tDeliveryAddress1 = tDeliveryAddressMapper.selectByPrimaryKey(Long.valueOf(addressReq.getAddressId()));
                if (tDeliveryAddress1 == null) {
                    throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20006);
                }
                TDeliveryAddressExample tDeliveryAddressExample = new TDeliveryAddressExample();
                tDeliveryAddressExample.createCriteria().andIdEqualTo(addressReq.getAddressId()).andCustomerIdEqualTo(addressReq.getOpenID());
                i = tDeliveryAddressMapper.deleteByExample(tDeliveryAddressExample);
                break;
            case "add":
                TDeliveryAddress tDeliveryAddress = new TDeliveryAddress();
                tDeliveryAddress.setCustomerId(addressReq.getOpenID());
                tDeliveryAddress.setDeliveryAddress(addressReq.getRAddress());
                tDeliveryAddress.setDeliveryName(addressReq.getRUserName());
                tDeliveryAddress.setDeliveryTel(addressReq.getRPhone());
                tDeliveryAddress.setMailNumber(addressReq.getZipCode());
                tDeliveryAddress.setIsDefault(addressReq.getIsDefault());
                i = tDeliveryAddressMapper.insertSelective(tDeliveryAddress);
                break;
        }
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20003);
        }
    }
}
