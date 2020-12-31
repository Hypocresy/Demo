package com.ai.fdop.monitor.mapper;

import com.ai.fdop.monitor.mode.entity.BillingAppInvokeLog;
import com.ai.fdop.monitor.mode.http.DataRequest;
import com.ai.fdop.monitor.mode.vo.TimeOutVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Mapper
@Repository
public interface BillingAppInvokeLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillingAppInvokeLog record);

    BillingAppInvokeLog selectByPrimaryKey(Long id);

    List<BillingAppInvokeLog> selectAll();

    int updateByPrimaryKey(BillingAppInvokeLog record);

    List<TimeOutVo> queryWeekOutTimeData(@Param("table") String table, @Param("request") DataRequest request);

    ArrayList<TimeOutVo> queryDayOutTimeData(String s, DataRequest request);
}