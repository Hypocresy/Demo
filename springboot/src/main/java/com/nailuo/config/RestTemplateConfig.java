package com.nailuo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/26 13:31
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}
