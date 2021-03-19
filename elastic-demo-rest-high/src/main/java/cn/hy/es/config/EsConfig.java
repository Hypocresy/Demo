package cn.hy.es.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/3 11:27
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Configuration
public class EsConfig {
    @Value("${elasticsearch.host}")
    private String hostname;
    @Value("${elasticsearch.port}")
    private String port;

    @Bean("restHighLevelClient")
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname,Integer.valueOf(port))));
        return  client;
    }
}
