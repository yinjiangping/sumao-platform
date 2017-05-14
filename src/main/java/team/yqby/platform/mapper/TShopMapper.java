package team.yqby.platform.mapper;

import org.apache.ibatis.annotations.Param;
import team.yqby.platform.pojo.TShop;
import team.yqby.platform.pojo.TShopExample;

import java.util.List;

public interface TShopMapper {
    int countByExample(TShopExample example);

    int deleteByExample(TShopExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TShop record);

    int insertSelective(TShop record);

    List<TShop> selectByExample(TShopExample example);

    TShop selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TShop record, @Param("example") TShopExample example);

    int updateByExample(@Param("record") TShop record, @Param("example") TShopExample example);

    int updateByPrimaryKeySelective(TShop record);

    int updateByPrimaryKey(TShop record);
}