package com.hy.demo.compont;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.hy.demo.mode.MerchantApp;
@Component
public class WriterRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    public void writer(){
        ValueOperations ops = redisTemplate.opsForValue();
        MerchantApp merchantApp = new MerchantApp();
        merchantApp.setApid("dasdasda");
        merchantApp.setAppid("dsadasd");
        merchantApp.setAppName("测试name");
        ops.set("name",merchantApp);
        System.out.println("写： "+ JSONArray.toJSONString(null));
    }
}
