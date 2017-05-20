package team.yqby.platform.controller;

import com.google.common.base.Joiner;
import com.qiniu.http.Response;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.common.util.QiNiuUtil;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.pojo.TFile;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class FileUploadController {
    @Autowired
    private TFileMapper tFileMapper;
    private final static String localPath = "/www/web/upload/";

    @RequestMapping("/uploadPic")
    public int uploadPic(@RequestParam("imgsFile") MultipartFile imgsFile, PrintWriter pw) {
        try {
           String format =  DateUtil.format(new Date(),DateUtil.shortDatePattern);
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                format += random.nextInt(10);
            }
            String ext = FilenameUtils.getExtension(imgsFile.getOriginalFilename());
            String fileName = format + "." + ext;
            String url = localPath + fileName;
            String path = "/upload/" + fileName;
            Client client = new Client();
            WebResource resource = client.resource(url);
            byte[] fileByteArray = imgsFile.getBytes();
            resource.put(fileByteArray);
            JSONObject jo = new JSONObject();
            jo.put("url", url);
            jo.put("path", path);
            //保存图片到服务器
            pw.write(jo.toString());
            //上传文件七牛云
            Response res = QiNiuUtil.upload(url, fileName);
            if (res.isOK()) {
                TFile tFile = new TFile();
                tFile.setFileAddress(PublicConfig.QINIU_URL + fileName);
                tFile.setFileName(fileName);
                tFile.setOrderId(0L);
                return tFileMapper.insertSelective(tFile);
            }
        } catch (Exception e) {
            log.error("paySign exception,error", e);
        }
        return 0;
    }

    /**
     * 删除图片
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/deletePic")
    public boolean queryByCode(Long fileId) {
        try {
            //1.判断图片是否已关联订单
            TFile file = tFileMapper.selectByPrimaryKey(fileId);
            if (file == null && file.getOrderId().longValue() <= 0L) {
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
            log.error("queryByCode exception,error", e);
        }
        return false;
    }

    public static void main(String[] args) {
        String format =  DateUtil.format(new Date(),DateUtil.shortDatePattern);
        System.out.println(format);
    }

}
