package xhx.pinganapi.pinganapiserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitDetail;
import xhx.pinganapi.pinganapiserver.mapper.XhxUserVisitDetailMapper;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Date;
import java.util.Map;

@Service
public class UserVisitDetailService {
    @Autowired
    XhxUserVisitDetailMapper userVisitDetailMapper;

    //添加一条用户访问信息
    public int insertVisit(XhxUserVisitDetail userVisitDetail){
        userVisitDetail.setLoginTime(new Date());
        return userVisitDetailMapper.insertVisit(userVisitDetail);
    }
    public int selectCount(Map map){
        return userVisitDetailMapper.selectCount(map);
    }
}
