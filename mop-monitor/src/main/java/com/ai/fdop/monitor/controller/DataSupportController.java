package com.ai.fdop.monitor.controller;


import cn.hutool.core.util.StrUtil;
import com.ai.fdop.monitor.enums.ErrorStatusEnum;
import com.ai.fdop.monitor.mode.vo.CommonResult;
import com.ai.fdop.monitor.mode.http.DataRequest;
import com.ai.fdop.monitor.service.DataSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/27 17:21
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@RestController
@RequestMapping("/queryData")
public class DataSupportController {

    @Autowired
    private DataSupportService supportService;
    @Autowired
    private Environment environment;

   @RequestMapping("/outTime/week")
    public CommonResult hebdomadDataWeek(@RequestBody DataRequest request){
        //参数校验
        if(StrUtil.isBlank(request.getStartDate())||StrUtil.isBlank(request.getEndDate())){
          return  CommonResult.of(ErrorStatusEnum.PARAM_DATE_ERR.getCode(),ErrorStatusEnum.PARAM_DATE_ERR.getMessage());
        }
        request.setOutTime(Long.valueOf(environment.getProperty("internet.timeOut")));
        CommonResult<Object> result = null;
        try {
            result = supportService.getWeekData(request);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResult.of(ErrorStatusEnum.UNKNOWN_REE.getCode(),ErrorStatusEnum.UNKNOWN_REE.getMessage());
        }
        return  result;
    }

    @RequestMapping("/out/day")
    public CommonResult hebdomadDataDay(@RequestBody DataRequest request){
        CommonResult<Object> result = null;
        if(StrUtil.isBlank(request.getStartDate())){
            return  CommonResult.of(ErrorStatusEnum.PARAM_DATE_ERR.getCode(),ErrorStatusEnum.PARAM_DATE_ERR.getMessage());
        }
        try {
            result =   supportService.getDayData(request);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResult.of(ErrorStatusEnum.UNKNOWN_REE.getCode(),ErrorStatusEnum.UNKNOWN_REE.getMessage());
        }
        return  result;
    }

}
