package com.ai.redis.controller;


import com.ai.redis.redisson.LockerUtil;
import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/12 10:47
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Controller
public class CoreController {
    public void aa()  {
        try{
            // 获取redis 锁
            LockerUtil.lock("dasdasda", TimeUnit.SECONDS,60);
            Thread.sleep(30000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            LockerUtil.unlock("dasdasda");
        }

    }
}
