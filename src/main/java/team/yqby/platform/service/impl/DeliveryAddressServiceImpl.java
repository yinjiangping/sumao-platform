package team.yqby.platform.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.yqby.platform.base.req.AddressReq;
import team.yqby.platform.base.res.AddressRes;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TDeliveryAddressMapper;
import team.yqby.platform.pojo.TDeliveryAddress;
import team.yqby.platform.pojo.TDeliveryAddressExample;
import team.yqby.platform.service.DeliveryAddressService;

import java.util.ArrayList;
import java.util.Date;
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
                criteria.andIsDefaultEqualTo("Y");
                break;
            case "no_default":
                criteria.andIsDefaultEqualTo("N");
                break;
        }
        List<TDeliveryAddress> deliveryAddressList = tDeliveryAddressMapper.selectByExample(tDeliveryAddressExample);
        List<AddressRes> addressResList = new ArrayList<>();
        for (TDeliveryAddress tDeliveryAddress : deliveryAddressList) {
            AddressRes addressRes = new AddressRes();
            addressRes.setRAddress(ParamsValidate.strDecode(tDeliveryAddress.getDeliveryAddress()));
            addressRes.setRUserName(ParamsValidate.strDecode(tDeliveryAddress.getDeliveryName()));
            addressRes.setRPhone(tDeliveryAddress.getDeliveryTel());
            addressRes.setZipCode(tDeliveryAddress.getMailNumber());
            addressRes.setIsDefault(tDeliveryAddress.getIsDefault());
            addressRes.setAddressId(tDeliveryAddress.getId());
            addressResList.add(addressRes);
        }
        return addressResList;
    }

    @Override
    public Long operaAddress(AddressReq addressReq, String operaFlag) {
        int i = 0;
        TDeliveryAddress tDeliveryAddress = new TDeliveryAddress();
        switch (operaFlag) {
            case "del":
                TDeliveryAddressExample tDeliveryAddressExample = new TDeliveryAddressExample();
                tDeliveryAddressExample.createCriteria().andIdEqualTo(addressReq.getAddressId()).andCustomerIdEqualTo(addressReq.getOpenID());
                tDeliveryAddress.setId(addressReq.getAddressId());
                List<TDeliveryAddress> tDeliveryAddressList = tDeliveryAddressMapper.selectByExample(tDeliveryAddressExample);
                if (tDeliveryAddressList == null || tDeliveryAddressList.isEmpty()) {
                    throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20006);
                }
                i = tDeliveryAddressMapper.deleteByExample(tDeliveryAddressExample);
                break;
            case "add":
                tDeliveryAddress.setCustomerId(addressReq.getOpenID());
                tDeliveryAddress.setDeliveryAddress(ParamsValidate.strEncode(addressReq.getRAddress()));
                tDeliveryAddress.setDeliveryName(ParamsValidate.strEncode(addressReq.getRUserName()));
                tDeliveryAddress.setDeliveryTel(addressReq.getRPhone());
                tDeliveryAddress.setMailNumber(addressReq.getZipCode());
                tDeliveryAddress.setUpdatetime(new Date());
                i = tDeliveryAddressMapper.insertSelective(tDeliveryAddress);
                break;
            case "edit":
                tDeliveryAddress.setId(addressReq.getAddressId());
                tDeliveryAddress.setCustomerId(addressReq.getOpenID());
                tDeliveryAddress.setDeliveryAddress(ParamsValidate.strEncode(addressReq.getRAddress()));
                tDeliveryAddress.setDeliveryName(ParamsValidate.strEncode(addressReq.getRUserName()));
                tDeliveryAddress.setDeliveryTel(addressReq.getRPhone());
                tDeliveryAddress.setMailNumber(addressReq.getZipCode());
                tDeliveryAddress.setUpdatetime(new Date());
                i = tDeliveryAddressMapper.updateByPrimaryKey(tDeliveryAddress);
                break;
            case "setDefault":
                TDeliveryAddressExample tDeliveryAddressExample1 = new TDeliveryAddressExample();
                TDeliveryAddressExample.Criteria criteria = tDeliveryAddressExample1.createCriteria();
                criteria.andCustomerIdEqualTo(addressReq.getOpenID());
                tDeliveryAddress.setIsDefault("N");
                tDeliveryAddressMapper.updateByExampleSelective(tDeliveryAddress, tDeliveryAddressExample1);
                TDeliveryAddress tDeliveryAddress1 = new TDeliveryAddress();
                tDeliveryAddress1.setId(addressReq.getAddressId());
                tDeliveryAddress1.setIsDefault("Y");
                tDeliveryAddress.setUpdatetime(new Date());
                i = tDeliveryAddressMapper.updateByPrimaryKeySelective(tDeliveryAddress1);
                break;
        }
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20003);
        }
        return tDeliveryAddress.getId();
    }
}
