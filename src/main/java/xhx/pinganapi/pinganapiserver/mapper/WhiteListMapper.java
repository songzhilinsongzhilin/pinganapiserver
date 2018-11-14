package xhx.pinganapi.pinganapiserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import xhx.pinganapi.pinganapiserver.bean.WhiteList;
@Mapper
public interface WhiteListMapper {
    WhiteList selectByIP(String loginIp);
}
