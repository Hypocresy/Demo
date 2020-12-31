package cn.hy.prometheus.compont;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/19 14:57
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Component
public class TestPromethus {
    @Autowired
    private MeterRegistry registry;
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void addiTtem(){
        Metrics.removeRegistry(registry);
        for (int i=0;i<1000;i++){
            double random = Math.random()*100;
            registry.gauge("rom", Tags.of("appid",String.valueOf(i),"test",String.valueOf(random)),(int) random);
        }
    }
}
