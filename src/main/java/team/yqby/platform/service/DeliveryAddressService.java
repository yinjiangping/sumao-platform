package team.yqby.platform.service;

import team.yqby.platform.base.req.AddressReq;
import team.yqby.platform.base.res.AddressRes;

import java.util.List;

public interface DeliveryAddressService{

    public List<AddressRes> queryAddress(String openID, String queryFlag);

    public void operaAddress(AddressReq addressReq, String operaFlag);
}
