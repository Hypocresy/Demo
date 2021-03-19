package com.nailuo.vo;

import com.nailuo.util.SM3Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/26 11:41
 * @since 0.0.1
 * 包装请求实体
 */
@Data
@Component
public class HttpRequest {
    @Autowired
    private Environment environment;
    private HashMap<String,Object> entity = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");

    public void init(){
        String secretId = environment.getProperty("secretId");
        String secreKey = environment.getProperty("secreKey");
        HashMap<String, String> interHash = new HashMap<>(32);
        String requestRefId =  "SJSREQ_"+sdf.format(new Date());
        String msg = "requestRefId="+requestRefId+"&secretId="+secretId;
        String sgin = SM3Util.getSignatureBySM3(msg, secreKey);
        interHash.put("requestRefId",requestRefId);
        interHash.put("signature",sgin);
        interHash.put("secretId",secretId);
        entity.put("head",interHash);
    }
}
