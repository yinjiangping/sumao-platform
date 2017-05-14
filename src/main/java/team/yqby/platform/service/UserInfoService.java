package team.yqby.platform.service;


import team.yqby.platform.pojo.TUser;

public interface UserInfoService {

    public TUser checkUser(String userName,String userPwd);

}
