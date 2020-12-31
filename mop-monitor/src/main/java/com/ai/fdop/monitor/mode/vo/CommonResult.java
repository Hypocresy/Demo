package com.ai.fdop.monitor.mode.vo;

import com.ai.fdop.monitor.enums.ErrorStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/27 17:32
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;
    public  CommonResult(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public  static  CommonResult of(Integer code,String msg){
     return    new CommonResult(code,msg);
    }
    public  static  CommonResult of(Integer code,String msg,Object data){
        return    new CommonResult(code,msg,data);
    }
    public  static  CommonResult of(Object data){
        return  new CommonResult(ErrorStatusEnum.SUCCESS.getCode(),ErrorStatusEnum.SUCCESS.getMessage(),data);
    }
}
