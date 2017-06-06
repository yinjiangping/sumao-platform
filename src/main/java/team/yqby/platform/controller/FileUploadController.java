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
import sun.security.krb5.internal.TGSRep;
import team.yqby.platform.base.Response;
import team.yqby.platform.base.TUserInfo;
import team.yqby.platform.base.res.GoodsRes;
import team.yqby.platform.base.res.SinglePicRes;
import team.yqby.platform.common.constant.SystemConstant;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.enums.PicPriceType;
import team.yqby.platform.common.util.DateUtil;
import team.yqby.platform.common.util.MoneyUtil;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.config.PublicConfig;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.manager.FileUploadThread;
import team.yqby.platform.mapper.TFileMapper;
import team.yqby.platform.mapper.TGoodsMapper;
import team.yqby.platform.pojo.TFile;
import team.yqby.platform.pojo.TGoods;
import team.yqby.platform.pojo.TGoodsExample;
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
    @Autowired
    private TGoodsMapper tGoodsMapper;

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
                log.error("uploadToken valid fail ,uploadToken:{},uploadTokenStr:{}", uploadToken, uploadTokenStr);
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
        TGoodsExample example = new TGoodsExample();
        List<TGoods> goodsList = tGoodsMapper.selectByExample(example);
        Map<String, String> goods = new HashMap<>();
        for (TGoods tGoods : goodsList) {
            goods.put(tGoods.getDesc(), MoneyUtil.changeF2Y(tGoods.getPrice()));
        }
        goodsRes.setGoods(goods);
        goodsRes.setFreightAmt(PublicConfig.FREIGHT_AMT);
        return goodsRes;
    }


}
