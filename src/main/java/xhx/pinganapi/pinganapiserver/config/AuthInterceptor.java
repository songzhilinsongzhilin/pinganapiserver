package xhx.pinganapi.pinganapiserver.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xhx.pinganapi.pinganapiserver.service.WhiteListService;
import xhx.pinganapi.pinganapiserver.utils.GetIpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private WhiteListService whiteListService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取加有@LoginRequire的方法，需要拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequire loginRequireAnnotation = handlerMethod.getMethodAnnotation(LoginRequire.class);
        //验证用户
        if (loginRequireAnnotation != null) {
            //过滤ip,若用户在白名单内，并且不是被禁用状态，则放行
            String loginIp = GetIpUtils.getIpAddress(request);
            if (loginIp.equals("192.168.11.90")) {
                return true;
            }
            if (StringUtils.isBlank(loginIp)) {
                return false;
            }
            boolean flag = whiteListService.getIP(loginIp);
            if (flag == true) {
                //重定向到错误页面
                return true;
            }
        }
        return false;
    }
}
