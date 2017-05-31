package team.yqby.platform.service;

import team.yqby.platform.pojo.TShop;

import java.util.List;

public interface ShopInfoService {

    public List<TShop> queryAll();

    public Long addShop(TShop tShop);

}
