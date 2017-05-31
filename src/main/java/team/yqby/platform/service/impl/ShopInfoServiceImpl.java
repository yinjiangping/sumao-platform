package team.yqby.platform.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TShopMapper;
import team.yqby.platform.pojo.TShop;
import team.yqby.platform.pojo.TShopExample;
import team.yqby.platform.service.ShopInfoService;

import java.util.List;

@Service("shopInfoService")
public class ShopInfoServiceImpl implements ShopInfoService {
    @Autowired
    private TShopMapper tShopMapper;

    @Override
    public List<TShop> queryAll() {
        TShopExample tShopExample = new TShopExample();
        List<TShop> tShops = tShopMapper.selectByExample(tShopExample);
        return tShops;
    }

    @Override
    public Long addShop(TShop tShop) {
        tShop.setShopName(ParamsValidate.strEncode(tShop.getShopName()));
        tShop.setShopAddress(ParamsValidate.strEncode(tShop.getShopAddress()));
        int i = tShopMapper.insertSelective(tShop);
        if (i == 0) {
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A20003);
        }
        return tShop.getId();
    }
}
