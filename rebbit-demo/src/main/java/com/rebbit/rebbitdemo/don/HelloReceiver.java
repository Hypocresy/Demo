package com.rebbit.rebbitdemo.don;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/10/15 16:18
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
public class HelloReceiver {
    @RabbitHandler
    public  void process(Map hello){
        System.out.println("接收到 ："+hello);
    }
}
