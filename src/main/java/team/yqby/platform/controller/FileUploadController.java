package team.yqby.platform.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.TUserInfo;
import team.yqby.platform.base.res.GoodsRes;
import team.yqby.platform.base.res.SinglePicRes;
import team.yqby.platform.common.constant.SystemConstant;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.PicPriceType;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.manager.FileUploadThread;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.pojo.TFile;
import team.yqby.platform.service.IRedisService;
import team.yqby.platform.service.TFileService;

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
    @Autowired
    private TFileService tFileService;

    @RequestMapping(value = ApiUrls.UPLOAD_PIC)
    @ResponseBody
    public SinglePicRes uploadPic(HttpServletRequest request) {
        Long picId = 0L;
        String imageUrl = "";
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
                return new SinglePicRes(picId, imageUrl);
            }
            MultipartFile file = files.get(0);
            String name = file.getOriginalFilename();
            String fileType = name.substring(name.lastIndexOf("."));
            String saveFilePath = Joiner.on("").join(localPath, format, fileType);
            String fileName = Joiner.on("").join(format, fileType);

            if (!file.isEmpty()) {
                //1、先插入数据库
                TFile tFile = new TFile();
                tFile.setFileAddress(Joiner.on("/").join(PublicConfig.QINIU_URL, fileName));
                tFile.setFileName(fileName);
                tFile.setFileNum(0L);
                tFile.setFileSize(0L);
                tFile.setSinglePrice("0");
                tFile.setCreatetime(new Date());
                tFile.setUpdatetime(new Date());
                tFileMapper.insertSelective(tFile);
                picId = tFile.getId();
                imageUrl = tFile.getFileAddress();
                //2、生成本地文件
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(saveFilePath)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    log.error("You failed to upload {} =>,{} ", name, e.getMessage());
                    return new SinglePicRes(picId, imageUrl);
                }
                //3.上传文件到七牛云(异步)
                FileUploadThread fileUploadThread = new FileUploadThread(saveFilePath, fileName, false);
                fileUploadThread.start();
            }

        } catch (Exception e) {
            log.error("uploadPic exception,error", e);
        }
        return new SinglePicRes(picId, imageUrl);
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

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (file.isEmpty()) {
                    continue;
                }
                //生成随机数字
                String format = DateUtil.format(new Date(), DateUtil.shortDatePattern);
                Random random = new Random();
                for (int j = 0; j < 10; j++) {
                    format += random.nextInt(10);
                }

                String name = file.getOriginalFilename();
                String fileType = name.substring(name.lastIndexOf("."));
                String saveFilePath = Joiner.on("").join(localPath, format, fileType);
                String fileName = Joiner.on("").join(format, fileType);

                //1.保存本地文件
                try {
                    byte[] bytes = file.getBytes();
                    file.getSize();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(saveFilePath)));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    log.error("You failed to upload {} =>,{} ", name, e.getMessage());
                    return idList;
                }
                //2、上传文件到七牛云
                FileUploadThread fileUploadThread = new FileUploadThread(saveFilePath, fileName, false);
                fileUploadThread.start();

                //3、保存上传文件数据
                TFile tFile = new TFile();
                tFile.setFileAddress(Joiner.on("/").join(PublicConfig.QINIU_URL, fileName));
                tFile.setFileName(fileName);
                tFile.setFileNum(0L);
                tFile.setFileSize(0L);
                tFile.setSinglePrice("0");
                tFile.setCreatetime(new Date());
                tFile.setUpdatetime(new Date());
                tFileMapper.insertSelective(tFile);
                idList.add(tFile);
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
    @RequestMapping(value = ApiUrls.QUERY_WARES_PRICE)
    @ResponseBody
    public GoodsRes queryWaresPrice(String openID) {
        GoodsRes goodsRes = new GoodsRes();
        String redisGoodsPrice = iRedisService.get(PublicConfig.GOODS_REDIS_KEY);
        if (StringUtils.isNotEmpty(redisGoodsPrice)) {
            goodsRes = JSON.parseObject(redisGoodsPrice, GoodsRes.class);
        }
        return goodsRes;
    }

    /**
     * 上传图片信息
     *
     * @param openID      用户编号
     * @param uploadToken 上传token
     * @return
     */
    @RequestMapping(value = ApiUrls.UPLOAD_PIC_INFO)
    @ResponseBody
    public Response<Long> uploadPicInfo(String openID, String uploadToken, String fileName) {
        try {
            log.info("uploadPicInfo started, request ");
            String uploadTokenStr = iRedisService.get(uploadToken.split(":")[1]);
            if (!StringUtils.equals(uploadTokenStr, uploadToken.split(":")[1])) {
                log.error("uploadToken valid fail ,uploadToken:{},uploadTokenStr:{}",uploadToken,uploadTokenStr);
                return new Response<>(ServiceErrorCode.ERROR_CODE_A20008);
            }
            Long id = tFileService.uploadFileInfo(fileName);
            log.info("uploadPicInfo finished,result:{}", id);
            return new Response<>(id);
        } catch (Exception e) {
            log.error(" uploadPicInfo meet error, response:{}", Throwables.getStackTraceAsString(e));
            return new Response<>(ServiceErrorCode.ERROR_CODE_F99999);
        }


    }
}
