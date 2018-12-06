package xhx.pinganapi.pinganapiserver.mapper;

import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitApi;

import java.util.Map;

public interface XhxUserVisitApiMapper {

    int insertVisit(Map map);

    XhxUserVisitApi selectByToken(String token);

    int updateLoginCount(XhxUserVisitApi userVisitApi);

    XhxUserVisitApi selectByTokenandloginIp(Map map);

    XhxUserVisitApi selectByTokenAndPermission(Map map);
}
