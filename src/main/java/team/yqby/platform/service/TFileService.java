package team.yqby.platform.service;

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.pojo.TFile;

import java.util.Date;

@Service
public class TFileService {
    @Autowired
    private TFileMapper tFileMapper;

    public Long uploadFileInfo(String fileName){
        TFile tFile = new TFile();
        tFile.setFileAddress(Joiner.on("/").join(PublicConfig.QINIU_URL, fileName));
        tFile.setFileName(fileName);
        tFile.setFileNum(0L);
        tFile.setFileSize("0");
        tFile.setSinglePrice("0");
        tFile.setCreatetime(new Date());
        tFile.setUpdatetime(new Date());
        tFileMapper.insertSelective(tFile);
        return tFile.getId();
    }
}
