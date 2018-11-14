package xhx.pinganapi.pinganapiserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitApi;
import xhx.pinganapi.pinganapiserver.mapper.XhxUserVisitApiMapper;
import xhx.pinganapi.pinganapiserver.utils.GetIpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class XhxUserVisitApiService {
    @Autowired
    XhxUserVisitApiMapper userVisitApiMapper;

    public int insertVisit(Map map) {
        return userVisitApiMapper.insertVisit(map);
    }

    //验证用户
    public Boolean verifytoken(String token){
        XhxUserVisitApi userVisitApi;
        userVisitApi = userVisitApiMapper.selectByToken(token);
        if(userVisitApi==null){
            return false;
        }
        return true;
    }
    //根据ip验证用户
    public Boolean verifytokenByLoginIp(HttpServletRequest request,String token){
        String loginIp = GetIpUtils.getIpAddress(request);
        Map map = new HashMap();
        map.put("loginIp",loginIp);
        map.put("token",token);
        XhxUserVisitApi userVisitApi;
        userVisitApi = userVisitApiMapper.selectByTokenandloginIp(map);
        if(userVisitApi==null){
            return false;
        }
        return true;
    }
    public int updateLoginCount(XhxUserVisitApi userVisitApi) {
        return userVisitApiMapper.updateLoginCount(userVisitApi);
    }

    public XhxUserVisitApi selectLoginCount(String token) {
        return userVisitApiMapper.selectByToken(token);
    }
}
