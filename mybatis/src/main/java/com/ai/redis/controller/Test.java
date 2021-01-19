package com.ai.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/12 13:34
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Component
public class Test implements CommandLineRunner {

 @Autowired
 private  CoreController controller;
    @Override
    public void run(String... strings) throws Exception {
        controller.aa();
    }
}
