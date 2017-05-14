package team.yqby.platform.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.yqby.platform.common.emodel.ServiceErrorCode;
import team.yqby.platform.common.util.ParamsValidate;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.mapper.TUserMapper;
import team.yqby.platform.pojo.TUser;
import team.yqby.platform.pojo.TUserExample;
import team.yqby.platform.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public TUser checkUser(String userName, String userPwd) {
        ParamsValidate.validaParam(userName,"用户名");
        ParamsValidate.validaParam(userName,"密码");
        TUserExample tUserExample = new TUserExample();
        tUserExample.createCriteria().andUsernameEqualTo(userName);
        List<TUser> userList = tUserMapper.selectByExample(tUserExample);
        //校验用户账号是否存在
        if(userList.size() <= 0){
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10012);
        }
        //校验用户密码是否正确
        TUser tUser = userList.get(0);
        if(!tUser.getPassword().equals(userPwd)){
            throw new AutoPlatformException(ServiceErrorCode.ERROR_CODE_A10013);
        }
        return tUser;
    }
}
