package com.hy.demo.controller;

import com.hy.demo.compont.ReadRedis;
import com.hy.demo.compont.WriterRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/27 9:47
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Component
public class CoreController implements CommandLineRunner {
    @Autowired
    private ReadRedis readRedis;
    @Autowired
    private WriterRedis writerRedis;
    @Override
    public void run(String... args) throws Exception {
        writerRedis.writer();
        Thread.sleep(1000);
        readRedis.read();
    }
}
