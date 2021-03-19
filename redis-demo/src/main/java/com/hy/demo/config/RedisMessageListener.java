package com.hy.demo.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/3/9 17:55
 * @since 0.0.1
 * redis 过期监听
 */
@Component
public class RedisMessageListener implements MessageListener {
;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
    }
}
