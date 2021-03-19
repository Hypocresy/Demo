package com.ai.fdop.monitor.service;


import cn.hutool.core.date.DateUtil;
import com.ai.fdop.monitor.enums.ErrorStatusEnum;
import com.ai.fdop.monitor.mapper.BillingAppInvokeLogMapper;

import com.ai.fdop.monitor.mode.http.TimeOutResponse;
import com.ai.fdop.monitor.mode.vo.CommonResult;
import com.ai.fdop.monitor.mode.http.DataRequest;

import com.ai.fdop.monitor.mode.vo.TimeOutVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/27 17:41
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Service

public class DataSupportService {
    @Autowired
    private BillingAppInvokeLogMapper billingAppInvokeLogMapper;
    private RedisTemplate redisTemplate;

    private SimpleDateFormat dsdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat hsdf= new SimpleDateFormat("yyyyMMddhh");
     /**
     * 获取7天时间内 fdop 互联网超时情况
      */
    public CommonResult<Object> getWeekData(DataRequest request) throws ParseException {
        TimeOutResponse data = new TimeOutResponse();
        Date startDate = dsdf.parse(request.getStartDate());
        Date endDate = dsdf.parse(request.getEndDate());
        request.setStartDateL(startDate.getTime());
        request.setEndDateL(DateUtil.offsetDay(endDate,1).getTime());
        List<String> intervalMonth = intervalMonth(startDate, endDate);
        ArrayList<TimeOutVo> appOutTiemItemList = new ArrayList<>();
        ArrayList<TimeOutVo> internetOutItemList = new ArrayList<>();
        intervalMonth.stream().forEach(item->{
            appOutTiemItemList.addAll(billingAppInvokeLogMapper.queryWeekOutTimeData("billing_app_invoke_log_"+item, request)) ;
            internetOutItemList.addAll(billingAppInvokeLogMapper.queryWeekOutTimeData("billing_internet_invoke_log_"+item, request));
        });

        List<String> intervalDay = intervalDay(startDate, endDate);
        ArrayList<Integer> appOutTimeList = new ArrayList<>();
        ArrayList<Integer> internetOutTimeList = new ArrayList<>();
        intervalDay.stream().forEach(item->{
            dataPadding(appOutTiemItemList, appOutTimeList, item);
            dataPadding(internetOutItemList, internetOutTimeList, item);

        });
        // 数据组装
         data.setLabel(intervalDay);
         data.setAppOutTimeTotal(appOutTimeList);
         data.setInternetOutTimeTotal(internetOutTimeList);
        return  CommonResult.of(ErrorStatusEnum.SUCCESS.getCode(),ErrorStatusEnum.SUCCESS.getMessage(),data);
    }



    public CommonResult<Object> getDayData(DataRequest request) throws ParseException {
        TimeOutResponse data = new TimeOutResponse();
        Date queryDate = dsdf.parse(request.getStartDate());
        String yyyymm = request.getStartDate().substring(0,6);
        ArrayList<String> list = new ArrayList<>();
        request.setStartDateL(queryDate.getTime());
        request.setEndDateL(DateUtil.offsetDay(queryDate,1).getTime());
        ArrayList<TimeOutVo> appOutTiemItemList  =   billingAppInvokeLogMapper.queryDayOutTimeData("billing_app_invoke_log_"+yyyymm, request);
        ArrayList<TimeOutVo> internetOutItemList = billingAppInvokeLogMapper.queryDayOutTimeData("billing_internet_invoke_log_"+yyyymm, request);
        ArrayList<Integer> appOutTiemList = new ArrayList<>();
        ArrayList<Integer> internetOutList = new ArrayList<>();
        //横坐标为24小时
        for(int i=0;i<24;i++){
            String hour= i<10?"0"+i:i+"";
            dataPadding(appOutTiemItemList,appOutTiemList,request.getStartDate()+hour);
            dataPadding(internetOutItemList,internetOutList,request.getStartDate()+hour);
            list.add(hour);
        }
        data.setAppOutTimeTotal(appOutTiemList);
        data.setInternetOutTimeTotal(internetOutList);
        data.setLabel(list);
        return  CommonResult.of(data);
    }


    private void dataPadding(ArrayList<TimeOutVo> internetOutItemList, ArrayList<Integer> internetOutTimeList, String item) {




        if(internetOutItemList.size()>0){
            internetOutItemList.stream().forEach(obj->{
                if(obj.getCeateDate().equals(item)){
                    internetOutTimeList.add(obj.getTotal());
                }else {
                    internetOutTimeList.add(0);
                }
            });
        }else {
            internetOutTimeList.add(0);
        }

    }

    /**
     *
     *获取两个时间相隔的的月份 以 yyyyMM 格式字符串返回
    */
    private  List<String> intervalMonth(Date start, Date end){
        List monthList =   new ArrayList<String>();
        Calendar startInstance = Calendar.getInstance();
        startInstance.setTime(start);
        int startYear = startInstance.get(Calendar.YEAR);
        int startMonth = startInstance.get(Calendar.MONTH);
        Calendar endInstance = Calendar.getInstance();
        startInstance.setTime(end);
        int endYear = endInstance.get(Calendar.YEAR);
        int endMonth = endInstance.get(Calendar.MONTH);
        if(startYear==endYear&&startMonth==endMonth){
            monthList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1)));
        }else {
            if(startYear==endYear){
                while(endMonth>=startMonth){
                    monthList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1)));
                    startMonth++;
                }
            }else {
                while(startYear<=endYear){
                    for(int i = startMonth;i<12;i++){
                        monthList.add(String.valueOf(startYear)+ ((i + 1)>9?(i + 1):"0"+(i + 1)));
                        if(startYear==endYear&&i==endMonth){
                            break;
                        }
                    }
                    startYear++;
                    startMonth=0;
                }
            }
        }
        return monthList;
    }





    /**
     *
     *获取两个时间相隔的的日期 以 yyyyMMdd 格式字符串返回
     */
    private    List<String> intervalDay(Date start, Date end){
        List dayList =   new ArrayList<String>();
        Calendar startInstance = Calendar.getInstance();
        startInstance.setTime(start);
        int startYear = startInstance.get(Calendar.YEAR);
        int startMonth = startInstance.get(Calendar.MONTH);
        int startDay = startInstance.get(Calendar.DAY_OF_MONTH);
        Calendar endInstance = Calendar.getInstance();
        startInstance.setTime(end);
        int endYear = endInstance.get(Calendar.YEAR);
        int endMonth = endInstance.get(Calendar.MONTH);
        int endDay = endInstance.get(Calendar.DAY_OF_MONTH);
        if(startYear==endYear&&startMonth==endMonth&&startDay==endDay){
            dayList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1))+""+(startDay  > 9 ? startDay  : "0" + startDay ));
        }else {
            if(startYear==endYear){
                while(endMonth>=startMonth){
                    Calendar tool = Calendar.getInstance();
                    tool.set(startYear,startMonth,startDay);
                    int maxDay = tool.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for(int i = startDay;i<=maxDay;i++){
                        dayList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1))+""+ (i  > 9 ? i  : "0" + i ));
                        if(startYear==endYear&&startMonth==endMonth&&startDay==endDay){
                            break;
                        }
                    }
                    startMonth++;
                    startDay=1;
                }
            }else {
                while(startYear<=endYear){
                    for(int i = startMonth;i<12;i++){
                        Calendar tool = Calendar.getInstance();
                        tool.set(startYear,i,startDay);
                        int maxDay = tool.getActualMaximum(Calendar.DAY_OF_MONTH);
                        for(int j = startDay;j<=maxDay;j++){
                            dayList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1))+""+ (j  > 9 ? j  : "0" + j ));
                            if(startYear==endYear&&startMonth==endMonth&&j==endDay){
                                break;
                            }
                        }
                        startMonth++;
                        startDay=1;
                    }
                    startYear++;
                    startMonth=0;
                }
            }
        }
        return dayList;
    }


}
