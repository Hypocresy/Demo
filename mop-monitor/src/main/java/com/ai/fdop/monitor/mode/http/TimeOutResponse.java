package com.ai.fdop.monitor.mode.http;

import lombok.Data;

import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/28 14:08
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Data
public class TimeOutResponse {
    private List<String> label;
    private List<Integer> appOutTimeTotal;
    private List<Integer> internetOutTimeTotal;
}
