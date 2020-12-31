package com.nailuo.vo;

import cn.hutool.crypto.digest.SM3;
import com.nailuo.util.SM3Util;
import com.nailuo.util.SM4Util;
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
 * 即使你忘记了我，我也不会遗忘你
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
        HashMap<String, String> interHash = new HashMap<>();
        String requestRefId =  "SJSREQ_201601010809108632A";  // "SJSREQ_"+sdf.format(new Date());
        String msg = "requestRefId="+requestRefId+"&secretId="+secretId;
        System.out.println("msg: "+msg);
        String sgin = SM3Util.getSignatureBySM3(msg, secreKey);

        interHash.put("requestRefId",requestRefId);
        interHash.put("signature",sgin);
        interHash.put("secretId",secretId);
        entity.put("head",interHash);
    }
}
