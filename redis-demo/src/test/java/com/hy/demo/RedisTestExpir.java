package com.hy.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/3/9 17:40
 * @since 0.0.1
 * 说明
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTestExpir {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
      public void ttt() throws InterruptedException {
          redisTemplate.opsForValue().set("llltest","1dasd", 2,TimeUnit.SECONDS);
          Thread.sleep(20000);
      }

}
