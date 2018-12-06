package xhx.pinganapi.pinganapiserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitDetail;

import java.util.Date;
import java.util.Map;

@Mapper
public interface XhxUserVisitDetailMapper {
    int insertVisit(XhxUserVisitDetail userVisitDetail);
    int selectCount(Map map);//String loginIp, Date startTime,Date endTime
}
