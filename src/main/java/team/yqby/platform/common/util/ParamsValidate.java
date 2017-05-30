package team.yqby.platform.common.util;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import team.yqby.platform.common.constant.SystemConstant;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.exception.AutoPlatformException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * <p>
 *      参数校验公共方法
 * </p>
 * User：jumping Date： 2017/1/5 0005 Version：1.0
 */
@Slf4j
public class ParamsValidate {

    /***
     * 校验参数
     * @param paramKey     参数名
     * @param paramDesc   参数描述
     */
    public static void validaParam(String paramKey,String paramDesc){
        if(StringUtils.isEmpty(paramKey)){
            log.error("{} validate error:{}不能为空",paramKey,paramDesc);
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10004.getResCode(), Joiner.on("").join(paramDesc,"不能为空"));
        }
    }
    /**
     * 验证码公共参数(包含切面错误)
     *
     * @param errors              错误
     * @return
     */
    public static void validParamError(Errors errors) {
        //字段错误
        if (errors.hasFieldErrors()) {
            log.error("param validate error:{}", errors.getFieldError().getDefaultMessage());
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10004.getResCode(), ServiceErrorCode.ERROR_CODE_A10004.getResDesc());
        }
        //其它错误
        if (errors.hasErrors()) {
            log.error("param other error:{}", errors.getAllErrors().get(0).getDefaultMessage());
            throw new AutoPlatformException(errors.getAllErrors().get(0).getCode(), errors.getAllErrors().get(0).getDefaultMessage());
        }

    }

    /***
     * 参数通过UTF-8解码
     * @param paramKey
     * @return
     */
    public static String strDecode(String paramKey){
        String newParamKey = paramKey;
        try {
            newParamKey = URLDecoder.decode(paramKey, SystemConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            log.error("Parameter {} decoding exception, ",newParamKey,e);
        }
        return newParamKey;
    }
}
