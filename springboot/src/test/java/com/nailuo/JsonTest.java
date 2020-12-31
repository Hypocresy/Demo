package com.nailuo;

import com.alibaba.fastjson.JSONObject;
import com.nailuo.util.SM4Util;
import org.junit.Test;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/12/18 15:03
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class JsonTest {
    @Test
    public  void jjjj() throws Exception {
        JSONObject object = new JSONObject();
        object.put("1213","dasdasd");
        String [] aa= {"dasd","asdasd"};
        object.put("arr",aa);
        String jsonString = JSONObject.toJSONString(object);
        String pass = SM4Util.encode(jsonString, "ZUNvcmN3cW14MXM5YTJ4bXdqODkxYXNk");
        String wo = SM4Util.decode(pass, "ZUNvcmN3cW14MXM5YTJ4bXdqODkxYXNk");
        System.out.println(wo);
        System.out.println("dasda");

    }
}
