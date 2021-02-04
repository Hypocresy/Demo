package cn.hy.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/2 16:28
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Data
@Document(indexName = "how2java",type = "product")
public class Product {
    private Integer id;
    private String code;
    private Double price;
    private String name;
    private String category;
    private String place;
}
