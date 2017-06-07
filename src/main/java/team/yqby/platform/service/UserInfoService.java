package team.yqby.platform.service;


import team.yqby.platform.base.TUserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserInfoService {

    public TUserInfo checkUser(String userName,String userPwd,HttpServletRequest request);

}
