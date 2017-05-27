package team.yqby.platform.controller;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import team.yqby.platform.common.constant.SystemConstant;
import team.yqby.platform.config.ApiUrls;
import team.yqby.platform.exception.AutoPlatformException;
import team.yqby.platform.pojo.TUser;
import team.yqby.platform.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登陆
     *
     * @param userName 用户名
     * @param userPwd  用户密码
     * @return
     */
    @RequestMapping(value = ApiUrls.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView login(String userName, String userPwd, HttpServletRequest request) {
        try {
            log.info("login started, request params:{},{}", userName,userPwd);
            TUser tUser = userInfoService.checkUser(userName,userPwd);

            request.getSession().setAttribute(SystemConstant.SESSION_USER, tUser);
            log.info("login finished, loginName:{}", userName);
        } catch (AutoPlatformException e) {
            log.error(" login meet error, loginName:{}, response:{}",userName, Throwables.getStackTraceAsString(e));
            return new ModelAndView("login").addObject("errorMsg", e.getMessage());
        }
        return new ModelAndView("index").addObject("loginResult",null);
    }

}
