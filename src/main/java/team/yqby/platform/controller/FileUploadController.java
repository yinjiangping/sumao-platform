package team.yqby.platform.controller;

import com.google.common.base.Joiner;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.utils.DateUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.manager.FileUploadThread;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.pojo.TFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Controller
public class FileUploadController {
    @Autowired
    private TFileMapper tFileMapper;
    private final static String localPath = "/www/web/upload/";

    @RequestMapping("/uploadPic")
    @ResponseBody
    public int uploadPic(HttpServletRequest request) {
        try {
            String format = DateUtil.format(new Date(), DateUtil.shortDatePattern);
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                format += random.nextInt(10);
            }
            List<MultipartFile> files;
            files = ((MultipartHttpServletRequest) request).getFiles("file");
            //文件不存在则直接抛出错误
            if (files == null || files.size() == 0) {
                log.error("upload file is employ，upload fail!");
                return 0;
            }
            MultipartFile file = files.get(0);
            String name = file.getName();
            String fileType = name.substring(name.lastIndexOf("."));
            String saveFilePath = Joiner.on("").join(localPath, format, fileType);
            String fileName = Joiner.on("").join(format,fileType);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(saveFilePath)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    log.error("You failed to upload {} =>,{} ", name, e.getMessage());
                    return 0;
                }
            }
            //上传文件七牛云  TODO 改成异步方式
            FileUploadThread fileUploadThread = new FileUploadThread(saveFilePath, fileName, false);
            fileUploadThread.start();

            TFile tFile = new TFile();
            tFile.setFileAddress(PublicConfig.QINIU_URL + fileName);
            tFile.setFileName(fileName);
            tFile.setOrderId(0L);
            return tFileMapper.insertSelective(tFile);
        } catch (Exception e) {
            log.error("uploadPic exception,error", e);
        }
        return 0;
    }

    public static void main(String[] args) {
        String test = "name.txt";
        System.out.println(test.substring(test.lastIndexOf(".")));
    }

    /**
     * 删除图片
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/deletePic")
    @ResponseBody
    public boolean deletePic(Long fileId) {
        try {
            //1.判断图片是否已关联订单
            TFile file = tFileMapper.selectByPrimaryKey(fileId);
            if (file == null || file.getOrderId().longValue() <= 0L) {
                log.error("origin pic no exists，delete fail!");
                return false;
            }
            //2.根据图片编号删除图片
            int i = tFileMapper.deleteByPrimaryKey(fileId);
            if (i > 0) {
                return true;
            }
            //3.删除本地服务器图片
            new File(Joiner.on("").join(localPath, file.getFileName())).deleteOnExit();
        } catch (Exception e) {
            log.error("deletePic exception,error", e);
        }
        return false;
    }

}
