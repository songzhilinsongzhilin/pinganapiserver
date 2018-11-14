package xhx.pinganapi.pinganapiserver.utils;

import xhx.pinganapi.pinganapiserver.bean.Result;
import xhx.pinganapi.pinganapiserver.enums.ResultEnum;

public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg("调取平安定制模块数据，包括企业字典表、注册信息、联系信息、员工人数、注册变更、裁判文书、法院公告、开庭公告、失信被执行人、被执行人、动产登记、行政处罚、清算信息、票据公示、进出口情况、主要进口/出口国家（地区）、主要进口/出口商品");
        result.setData(object);
        return result;
    }
    public static Result success(Object object,String msg) {
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    public static Result success() {
        return success(null);
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }
}
