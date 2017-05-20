package team.yqby.platform.manager;


import lombok.extern.slf4j.Slf4j;
import team.yqby.platform.common.util.QiNiuYunUtil;

import java.io.IOException;

@Slf4j
public class FileUploadThread extends Thread {
    private boolean runFlag;
    private String localFilePath;
    private String fileName;
    public FileUploadThread(String localFilePath,String fileName,boolean runFlag){
        this.localFilePath = localFilePath;
        this.fileName = fileName;
        this.runFlag = runFlag;
    }
    @Override
    public void run() {
        if(!runFlag){
            try {
                QiNiuYunUtil.upload(localFilePath, fileName);
            } catch (IOException e) {
                log.error("File {} upload QiNiuYun file fail,error",fileName,e);
            }
        }
        this.runFlag = true;
    }
}
