package xhx.pinganapi.pinganapiserver.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitDetail;
import xhx.pinganapi.pinganapiserver.config.LoginRequire;
import xhx.pinganapi.pinganapiserver.service.UserVisitDetailService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/visitDetail")
public class UserVisitDetailController {
    @Autowired
    UserVisitDetailService userVisitDetailService;
    //添加一条用户访问信息
    @LoginRequire
    @ResponseBody
    @RequestMapping("/visitDetailInsertVisit")
    public int insertVisit(XhxUserVisitDetail userVisitDetail){
        return userVisitDetailService.insertVisit(userVisitDetail);
    }

    //根据开始时间与结束时间和用户IP查询访问次数
    @LoginRequire
    @ResponseBody
    @RequestMapping("/visitDetailSelectCount")
    public int selectCount(HttpServletRequest request){//String loginIp, Date startTime, Date endTime
        Map map = new HashMap();
        String data=request.getParameter("data");
        if (StringUtils.isNotBlank(data)){
            map = JSON.parseObject(data,map.getClass());
        }
        return userVisitDetailService.selectCount(map);
    }

}
