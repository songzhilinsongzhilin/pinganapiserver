package xhx.pinganapi.pinganapiserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhx.pinganapi.pinganapiserver.bean.WhiteList;
import xhx.pinganapi.pinganapiserver.mapper.WhiteListMapper;
import xhx.pinganapi.pinganapiserver.utils.SnowFlake;

import java.util.Date;
@Service
@Transactional
public class WhiteListService {
    private WhiteList whiteList;
    @Autowired
    private WhiteListMapper whiteListMapper;
    public Boolean getIP(String loginIp){
        whiteList = whiteListMapper.selectByIP(loginIp);
        if (whiteList==null){
            return false;
        }
        return true;
    }

    public int addIp(String IP){
        whiteList.setLoginIp(IP);
        whiteList.setCreateTime(new Date());
        whiteList.setUpdateTime(new Date());
        whiteList.setId(SnowFlake.getSnowFlakeId("xhx_white_list"));
        int i=0;
        return i;
    }
}
