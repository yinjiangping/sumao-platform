package team.yqby.platform.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.yqby.platform.base.res.GoodsRes;
import team.yqby.platform.common.enums.PicPriceType;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.manager.FileUploadThread;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.pojo.TFile;
import team.yqby.platform.service.IRedisService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Slf4j
@Controller
public class FileUploadController {
    @Autowired
    private TFileMapper tFileMapper;
    private final static String localPath = "/www/web/upload/";
    @Autowired
    private IRedisService iRedisService;

    @RequestMapping(value = ApiUrls.UPLOAD_PIC)
    @ResponseBody
    public Long uploadPic(HttpServletRequest request) {
        try {
            String format = DateUtil.format(new Date(), DateUtil.shortDatePattern);
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                format += random.nextInt(10);
            }
            //查询ID是否存在
            TFile file1 = tFileMapper.selectByPrimaryKey(Long.valueOf(format));
            if (file1 != null) {
                log.error("upload file is exists，upload fail!");
                return 0L;
            }
            List<MultipartFile> files;
            files = ((MultipartHttpServletRequest) request).getFiles("file");
            //文件不存在则直接抛出错误
            if (files == null || files.size() == 0) {
                log.error("upload file is employ，upload fail!");
                return 0L;
            }
            MultipartFile file = files.get(0);
            String name = file.getOriginalFilename();
            String fileType = name.substring(name.lastIndexOf("."));
            String saveFilePath = Joiner.on("").join(localPath, format, fileType);
            String fileName = Joiner.on("").join(format, fileType);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(saveFilePath)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    log.error("You failed to upload {} =>,{} ", name, e.getMessage());
                    return 0L;
                }
            }
            //上传文件七牛云  TODO 改成异步方式
            FileUploadThread fileUploadThread = new FileUploadThread(saveFilePath, fileName, false);
            fileUploadThread.start();
            TFile tFile = new TFile();
            tFile.setId(Long.valueOf(format));
            tFile.setFileAddress(Joiner.on("/").join(PublicConfig.QINIU_URL, fileName));
            tFile.setFileName(fileName);
            tFile.setCreatetime(new Date());
            tFile.setUpdatetime(new Date());
            tFileMapper.insertSelective(tFile);
            return Long.valueOf(format);
        } catch (Exception e) {
            log.error("uploadPic exception,error", e);
        }
        return 0L;
    }

    @RequestMapping(value = ApiUrls.UPLOAD_MULTIPLE_PIC)
    @ResponseBody
    public List<TFile> uploadMultiplePic(HttpServletRequest request) {
        List<TFile> idList = new ArrayList<>();
        try {
            List<MultipartFile> files;
            files = ((MultipartHttpServletRequest) request).getFiles("file");
            //文件不存在则直接抛出错误
            if (files == null || files.size() == 0) {
                log.error("upload file is employ，upload fail!");
                return idList;
            }
            MultipartFile file = files.get(0);

            for (int i = 0; i < files.size(); i++) {
                String format = DateUtil.format(new Date(), DateUtil.shortDatePattern);
                Random random = new Random();
                for (int j = 0; j < 10; j++) {
                    format += random.nextInt(10);
                }
                String name = file.getOriginalFilename();
                String fileType = name.substring(name.lastIndexOf("."));
                String saveFilePath = Joiner.on("").join(localPath, format, fileType);
                String fileName = Joiner.on("").join(format, fileType);

                if (!file.isEmpty()) {
                    //1、上传文件到七牛云
                    FileUploadThread fileUploadThread = new FileUploadThread(saveFilePath, fileName, false);
                    fileUploadThread.start();
                    //2、保存上传文件数据
                    TFile tFile = new TFile();
                    tFile.setFileAddress(Joiner.on("/").join(PublicConfig.QINIU_URL, fileName));
                    tFile.setFileName(fileName);
                    tFile.setCreatetime(new Date());
                    tFile.setUpdatetime(new Date());
                    tFileMapper.insertSelective(tFile);
                    try {
                        byte[] bytes = file.getBytes();
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(saveFilePath)));
                        stream.write(bytes);
                        stream.close();
                    } catch (Exception e) {
                        log.error("You failed to upload {} =>,{} ", name, e.getMessage());
                        return idList;
                    }
                    idList.add(tFile);
                }
            }
        } catch (Exception e) {
            log.error("uploadPic exception,error", e);
        }
        return idList;
    }

    /**
     * 删除图片
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = ApiUrls.DELETE_PIC)
    @ResponseBody
    public boolean deletePic(Long fileId) {
        try {
            //1.判断图片是否已关联订单
            TFile file = tFileMapper.selectByPrimaryKey(fileId);
            if (file == null || !StringUtils.isEmpty(file.getOrderId())) {
                log.error("pic does not exist or cannot be deleted!");
                return false;
            }
            //2.根据图片编号删除图片
            int i = tFileMapper.deleteByPrimaryKey(fileId);
            if (i > 0) {
                //3.删除本地服务器图片
                new File(Joiner.on("").join(localPath, file.getFileName())).deleteOnExit();
            }
            return true;
        } catch (Exception e) {
            log.error("deletePic exception,error", e);
        }
        return false;
    }


    /**
     * 查询商品价格
     *
     * @param openID 用户编号
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_GOODS_PRICE)
    @ResponseBody
    public Map<String, String> queryPrice(String openID) {
        Map<String, String> priceMap = new HashMap<>();
        for (PicPriceType picPriceType : PicPriceType.values()) {
            priceMap.put(picPriceType.getPicMark(), picPriceType.getPicPrice());
        }
        return priceMap;
    }

    /**
     * 查询商品价格
     *
     * @param openID 用户编号
     * @return
     */
    @RequestMapping(value = ApiUrls.QUERY_WARES_PRICE)
    @ResponseBody
    public GoodsRes queryWaresPrice(String openID) {
        GoodsRes goodsRes = new GoodsRes();
        String redisGoodsPrice = iRedisService.get(PublicConfig.GOODS_REDIS_KEY);
        if(StringUtils.isNotEmpty(redisGoodsPrice)){
            goodsRes = JSON.parseObject(redisGoodsPrice,GoodsRes.class);
        }
        return goodsRes;
    }
}
