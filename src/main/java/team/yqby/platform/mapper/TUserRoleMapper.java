package team.yqby.platform.mapper;

import org.apache.ibatis.annotations.Param;
import team.yqby.platform.pojo.TUserRole;
import team.yqby.platform.pojo.TUserRoleExample;

import java.util.List;

public interface TUserRoleMapper {
    int countByExample(TUserRoleExample example);

    int deleteByExample(TUserRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    List<TUserRole> selectByExample(TUserRoleExample example);

    TUserRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TUserRole record, @Param("example") TUserRoleExample example);

    int updateByExample(@Param("record") TUserRole record, @Param("example") TUserRoleExample example);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);
}