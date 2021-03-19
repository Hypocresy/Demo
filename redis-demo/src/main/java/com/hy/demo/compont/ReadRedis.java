package com.hy.demo.compont;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.hy.demo.entity.MerchantApp;
@Component
public class ReadRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    public void read(){
        ValueOperations<String,MerchantApp> ops = redisTemplate.opsForValue();
        MerchantApp name = ops.get("name");
//        Gson gson = new Gson();
//        MerchantApp name = gson.fromJson(ops.get("name"), MerchantApp.class);
//        MerchantApp name = JSONObject.parseObject(ops.get("name"),MerchantApp.class);
//        if(name==null){
//            System.out.println("读： "+JSONArray.toJSONString(name));
//        }
//        System.out.println("读： "+JSONArray.toJSONString(name));

    }
}
