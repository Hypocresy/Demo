package com.hy.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/3/9 18:03
 * @since 0.0.1
 * 说明
 */
@Configuration
public class PubsubConfiguration {

    @Autowired
    private RedisMessageListener redisMessageListener;


    @Bean
    public ChannelTopic expiredTopic() {
        return new ChannelTopic("__keyevent@0__:expired");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            @Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(redisMessageListener, expiredTopic());
        return redisMessageListenerContainer;
    }

}
