package cn.hy.redis.utils.redisson;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-09-19 17:11
 */
@Configuration
public class RedissonConfig {



    @Bean
    public RedissonLocker redissonLocker(@Autowired RedissonClient redissonClient){

        RedissonLocker redissonLocker = new RedissonLocker(redissonClient);
        RAtomicLong redisson = redissonClient.getAtomicLong("redisson_test_key");
        redisson.set(1000);

        long l = redisson.get();
        System.out.println("初始值  "+ l);
        LockerUtil.setLocker(redissonLocker);

        return redissonLocker;
    }

    @Bean
    public RedissonClient redisson() throws IOException {
        File file = ResourceUtils.getFile("classpath:redisson-config.yml");
        Config config = Config.fromYAML(file);
        return Redisson.create(config);
    }
}
