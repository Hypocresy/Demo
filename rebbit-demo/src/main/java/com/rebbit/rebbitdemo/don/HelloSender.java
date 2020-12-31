package com.rebbit.rebbitdemo.don;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/10/15 16:08
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Component
public class HelloSender {
    @Autowired
    private RabbitTemplate amqpTemplate;

    public String send() {
        String messageID = String.valueOf(UUID.randomUUID());
        String messageData = "test message hello";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, String> map = new HashMap<>();
        map.put("messageID", messageID);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        amqpTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
}
