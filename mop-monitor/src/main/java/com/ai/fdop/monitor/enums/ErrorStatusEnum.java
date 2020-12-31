package com.ai.fdop.monitor.enums;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/28 10:06
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
public enum ErrorStatusEnum {

    SUCCESS(103000, "返回成功"),
    DATA_NO_ERR(103101,"数据库查询错误" ),
    PARAM_OTHER_ERR(103113,"参数校验错误"),
    UNKNOWN_REE(103112,"程序未知错误"),
    PARAM_DATE_ERR(103119,"请输入开始或结束日期");

    private final Integer code;
    private final String message;

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    ErrorStatusEnum(Integer code, String message){
        this.code= code;
        this.message=message;
    }

}
