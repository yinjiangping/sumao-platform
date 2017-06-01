package team.yqby.platform.service;


import team.yqby.platform.base.TUserInfo;

public interface UserInfoService {

    public TUserInfo checkUser(String userName,String userPwd);

}
