package team.yqby.platform.mapper;

import org.apache.ibatis.annotations.Param;
import team.yqby.platform.pojo.TDeliveryAddress;
import team.yqby.platform.pojo.TDeliveryAddressExample;

import java.util.List;

public interface TDeliveryAddressMapper {
    int countByExample(TDeliveryAddressExample example);

    int deleteByExample(TDeliveryAddressExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TDeliveryAddress record);

    int insertSelective(TDeliveryAddress record);

    List<TDeliveryAddress> selectByExample(TDeliveryAddressExample example);

    TDeliveryAddress selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TDeliveryAddress record, @Param("example") TDeliveryAddressExample example);

    int updateByExample(@Param("record") TDeliveryAddress record, @Param("example") TDeliveryAddressExample example);

    int updateByPrimaryKeySelective(TDeliveryAddress record);

    int updateByPrimaryKey(TDeliveryAddress record);
}