package com.rebbit.rebbitdemo;

import com.rebbit.rebbitdemo.don.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RebbitDemoApplicationTests {
   @Autowired
   private HelloSender helloSender;

    @Test
    public void contextLoads() throws InterruptedException {
        helloSender.send();
        TimeUnit.SECONDS.sleep(2);
    }

}
