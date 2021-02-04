package cn.hy.es.dao;

import cn.hy.es.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/2 16:30
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public interface ProductRepository extends ElasticsearchRepository<Product,Integer> {
    List<Product> findByName(String usename, Pageable request);

}
