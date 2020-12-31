package com.ai.fdop.monitor.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/28 11:10
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
public class DataTest {

    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Date date1 = sdf.parse("20200611");
        Calendar instance = Calendar.getInstance();
        instance.set(2020,6,12);
        Date time = instance.getTime();
        Date date = new Date();
//        List<String> strings = intervalMonth(time, date);
        List<String> strings = intervalDay(time, date);
                System.out.println(strings);
    }
    public static List<String> intervalMonth(Date start, Date end){
        List yearList =   new ArrayList<String>();
        Calendar startInstance = Calendar.getInstance();
        startInstance.setTime(start);
        int startYear = startInstance.get(Calendar.YEAR);
        int startMonth = startInstance.get(Calendar.MONTH);
        Calendar endInstance = Calendar.getInstance();
        startInstance.setTime(end);
        int endYear = endInstance.get(Calendar.YEAR);
        int endMonth = endInstance.get(Calendar.MONTH);
        if(startYear==endYear&&startMonth==endMonth){
            yearList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1)));
        }else {
            if(startYear==endYear){
                while(endMonth>=startMonth){
                    yearList.add(String.valueOf(startYear)+ ((startMonth + 1)>9?(startMonth + 1):"0"+(startMonth + 1)));
                    startMonth++;
                }
            }else {
                while(startYear<=endYear){
                    for(int i = startMonth;i<12;i++){
                        yearList.add(String.valueOf(startYear)+ ((i + 1)>9?(i + 1):"0"+(i + 1)));
                        if(startYear==endYear&&i==endMonth){
                            break;
                        }
                    }
                    startYear++;
                    startMonth=0;
                }
            }
        }
        return yearList;
    }



    /**
     *
     *获取两个时间相隔的的日期 以 yyyyMMdd 格式字符串返回
     */
    private static   List<String> intervalDay(Date start, Date end){
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
