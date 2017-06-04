package team.yqby.platform.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
public class SpringBootController {

    @RequestMapping("/")
    public ModelAndView page1() {
        return new ModelAndView("login");
    }

    /**
     * 跳转功能页面
     *
     * @param pageId  通过功能编号跳转到任意页面
     * @param request 请求对象
     * @return
     */
    @RequestMapping(value = "/forwardFunPage", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView forwardFunPage(String pageId, String pageUrl, HttpServletRequest request) {
        Map map = new HashMap<>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            map.put(paraName, request.getParameter(paraName));
        }
        //TODO 后期添加权限控制
        return new ModelAndView(pageUrl).addObject("functions", "update,query").addObject("paramMaps", map);
    }

}
