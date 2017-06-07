package team.yqby.platform.service.impl;

import java.util.List;


import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.yqby.platform.base.TUserInfo;
import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.util.InternetProtocol;
import team.yqby.platform.common.util.MD5Util;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TShopMapper;
import team.yqby.platform.mapper.TUserMapper;
import team.yqby.platform.pojo.TShop;
import team.yqby.platform.pojo.TShopExample;
import team.yqby.platform.pojo.TUser;
import team.yqby.platform.pojo.TUserExample;
import team.yqby.platform.service.IRedisService;
import team.yqby.platform.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private TShopMapper tShopMapper;
    @Autowired
    private IRedisService redisService;

    @Override
    public TUserInfo checkUser(String userName, String userPwd, HttpServletRequest request) {
        ParamsValidate.validaParam(userName, "用户名");
        ParamsValidate.validaParam(userName, "密码");
        //检查登陆失败次数
        callIpRequest(request, "check");
        TUserExample tUserExample = new TUserExample();
        tUserExample.createCriteria().andUsernameEqualTo(userName);
        List<TUser> userList = tUserMapper.selectByExample(tUserExample);
        //校验用户账号是否存在
        if (userList.size() <= 0) {
            //统计登陆失败次数
            callIpRequest(request, "add");
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10012);
        }
        //校验用户密码是否正确
        TUser tUser = userList.get(0);
        if (!tUser.getPassword().equals(MD5Util.MD5Encode(userPwd))) {
            //统计登陆失败次数
            callIpRequest(request, "add");
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10013);
        }
        //清除错误次数
        callIpRequest(request, "clear");

        TUserInfo tUserInfo = new TUserInfo();
        //查询门店信息
        TShopExample tShopExample = new TShopExample();
        tShopExample.createCriteria().andUserIdEqualTo(tUser.getId());
        List<TShop> tShops = tShopMapper.selectByExample(tShopExample);
        if (tShops != null && !tShops.isEmpty()) {
            tUserInfo.setShopId(tShops.get(0).getId());
        }
        tUserInfo.setId(tUser.getId());
        tUserInfo.setUsername(tUser.getUsername());
        tUserInfo.setPassword(tUser.getPassword());
        return tUserInfo;
    }


    /***
     * 防止登陆攻击
     *
     * @param request
     * @param operaType
     */
    public void callIpRequest(HttpServletRequest request, String operaType) {
        String remoteIp = InternetProtocol.getRemoteAddr(request);
        String remotePort = InternetProtocol.getRemoteAddr(request);
        String redisKey = Joiner.on("_").join(remoteIp, remotePort);
        switch (operaType) {
            case "add":
                String countNumStr = redisService.get(redisKey);
                int countNumInt = StringUtils.isEmpty(countNumStr) ? 0 : Integer.parseInt(countNumStr);
                redisService.set(redisKey, String.valueOf(countNumInt + 1), 60 * 15L);
                break;
            case "clear":
                redisService.del(redisKey);
                break;
            case "check":
                String countNumStr2 = redisService.get(redisKey);
                if (StringUtils.isNotEmpty(countNumStr2) && Integer.parseInt(countNumStr2) >= 3) {
                    throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10014);
                }
        }
    }
}
