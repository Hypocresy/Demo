package cn.hy.es.dao;

import cn.hy.es.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/2 15:32
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public interface CustomeRepositoin extends ElasticsearchRepository<Customer,Integer> {
}
