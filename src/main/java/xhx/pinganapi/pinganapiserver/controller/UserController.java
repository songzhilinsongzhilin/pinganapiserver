package xhx.pinganapi.pinganapiserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhx.pinganapi.pinganapiserver.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        String token = request.getParameter("token");
        String currentIp = request.getParameter("currentIp");//获取用户的ip地址，相当于salt盐值
        // 检查token

        /*Map<String, Object> map = JwtUtil.decode(token, signKey, currentIp);
        if (map!=null){
            // 检查redis信息
            String userId = (String) map.get("userId");
            UserInfo userInfo = userInfoService.verify(userId);
            if (userInfo!=null){
                return "success";
            }*/

        return "fail";
    }

}
