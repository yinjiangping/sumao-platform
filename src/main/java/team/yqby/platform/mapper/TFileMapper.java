package team.yqby.platform.mapper;

import org.apache.ibatis.annotations.Param;
import team.yqby.platform.pojo.TFile;
import team.yqby.platform.pojo.TFileExample;

import java.util.List;

public interface TFileMapper {
    int countByExample(TFileExample example);

    int deleteByExample(TFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TFile record);

    int insertSelective(TFile record);

    List<TFile> selectByExample(TFileExample example);

    TFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TFile record, @Param("example") TFileExample example);

    int updateByExample(@Param("record") TFile record, @Param("example") TFileExample example);

    int updateByPrimaryKeySelective(TFile record);

    int updateByPrimaryKey(TFile record);
}