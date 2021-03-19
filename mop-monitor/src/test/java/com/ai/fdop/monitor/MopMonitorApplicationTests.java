package com.ai.fdop.monitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest

class MopMonitorApplicationTests {
   @Autowired
   private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
         Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            try (Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
                    .match("app_*")
                    .count(4).build())) {
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next(), "Utf-8"));
                }
            } catch (Exception e) {
//                LOGGER.error("获取app keys失败",e.getMessage());
                throw new RuntimeException(e);
            }
            return keysTmp;
        });
        System.out.println(keys);
    }

}
