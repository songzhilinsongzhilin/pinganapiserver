package xhx.pinganapi.pinganapiserver.enums;

public enum ResultEnum {
    SUCCESS(1, "成功"),
    NOT_FIND(0, "未查询到值"),
    LACK_PARAMETER(-1,"缺少参数"),
    CHECK_SUCCESS(200,"验证成功"),
    ADD_SUCCESS(201, "添加成功"),
    ADD_ERROR(202, "添加失败"),
    CHECKFAILPERMISSION(203,"访问权限不足"),
    CHECK_ERROR(500,"验证失败");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
