package com.ai.fdop.monitor.mode.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/27 17:38
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRequest {
    private Long appId;
    private String startDate;
    private String endDate;
    private Integer limit;
    private Long startDateL;
    private Long endDateL;
    private Long outTime;
}
