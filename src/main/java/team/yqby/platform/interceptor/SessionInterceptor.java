package team.yqby.platform.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import team.yqby.platform.base.TUserInfo;
import team.yqby.platform.common.constant.SystemConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.regex.Pattern;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    /**
     * 过滤不校验session接口列表*
     */
    private String noCheck = "/js/.*|/css/.*|/image/.*|/style/.*|/WEB-INF/.|error|.jsp|.txt|.html|.jpeg|.png|.gif|/queryOpenID|/paySign|/uploadPicInfo|/deletePic|/createOrder|/confirmOrder|/payCallBack|/editAddress|/addAddress|/delAddress|/setDefaultAddress|/queryAddress|/confirmOrder|/createOrder|/queryOrder|/deleteOrder|/queryShop|/queryWaresPrice|/getUploadToken";

    private static final String LOGIN_URL = "/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String visitUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        visitUri = visitUri.replace(contextPath, "");
        HttpSession session = request.getSession(true);
        Pattern p = Pattern.compile(noCheck);
        if (p.matcher(visitUri).find()) {
            Enumeration<String> e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String value = request.getParameter(e.nextElement());
                if (value.contains("<") || value.contains(">")) {
                    log.error("请求地址：{}，请求参数格式有误，参数:{}", visitUri, value);
                    response.sendRedirect(request.getSession().getServletContext().getContextPath() + LOGIN_URL);
                    return false;
                }
            }
            return true;
        }

        TUserInfo tUserInfo = (TUserInfo) session.getAttribute(SystemConstant.SESSION_USER);
        if ("/".equals(visitUri)) {
            return true;
        }
        if (tUserInfo == null) {
            log.error("请求地址:{},用户长时间未登陆或在其它地方登陆，跳转到登陆页面", visitUri);
            response.sendRedirect(request.getSession().getServletContext().getContextPath() + LOGIN_URL);
            return false;
        }
        session.setAttribute(SystemConstant.SESSION_USER, tUserInfo);
        log.info("登陆账号：{}，SESSION验证通过！", tUserInfo.getUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}

