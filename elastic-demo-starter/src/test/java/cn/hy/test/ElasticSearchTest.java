package cn.hy.test;


import cn.hy.es.dao.CustomeRepositoin;
import cn.hy.es.dao.ProductRepository;
import cn.hy.es.entity.Customer;
import cn.hy.es.entity.Product;
import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/1 21:35
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {
    @Autowired
    private CustomeRepositoin repository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * es 批量添加数据
     */
    @Test
    public void esAdd() {
        List<Customer> list = new ArrayList(100000);
        System.out.println("start tiem :" + System.currentTimeMillis());
        for (int i = 0; i < 100000; i++) {
            list.add(new Customer(i, UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }
        System.out.println("midway tiem :" + System.currentTimeMillis());
        repository.saveAll(list);
        System.out.println("end tiem :" + System.currentTimeMillis());
    }

    /**
     * 删除数据
     */
    @Test
    public void esDelete() {
        //按条件删除一条数据
        Customer customer = repository.findById(1).orElse(null);
        repository.delete(customer);
        //删除所有索引下，对应 type的所有数据
        repository.deleteAll();
    }

    /**
     * 修改数据
     */
    @Test
    public void esUpdate() {
        Customer customer = repository.findById(1).orElse(null);
        customer.setAddress("广州");
        customer.setUseruame("测试2");
        repository.save(customer);
    }

    /**
     * 分页 按id倒序
     */
    @Test
    public void fetchPageCustomen() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // 0为首页  1为开始页  15为每页大小
        Pageable request = PageRequest.of(1, 15, sort);
        Page<Customer> all = repository.findAll(request);
        System.out.println("all: " + all.getContent().toString());

    }

    /**
     * 按 price 正序 ，分页
     */
    @Test
    public void fetchPageProduct() {
        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        PageRequest request = PageRequest.of(0, 15, sort);
        List content = productRepository.findAll(request).getContent();
        System.out.println("product all: " + JSONArray.toJSONString(content));
    }

    /**
     * queryBuilder查询  查询 place 为北京的
     * <p>
     * must(QueryBuilders):AND
     * mustNot(QueryBuilders):NOT
     * should::OR
     */
    @Test
    public void fetchQueryBuilderProduct() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("place", "北京"));
        List<Product> content = productRepository.search(queryBuilder, PageRequest.of(0, 15)).getContent();
        System.out.println("content: " + JSONArray.toJSONString(content));
    }

    /**
     * 精准查询
     * 单个匹配
     */
    @Test
    public void preciseQuery() {
        //不使用分词器
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("price", 10d);
        List<Product> content = productRepository.search(termQueryBuilder, PageRequest.of(0, 10)).getContent();
        System.out.println("content: " + JSONArray.toJSONString(content));
        //使用分词器
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("price", 10d);
        List<Product> content1 = productRepository.search(matchQuery, PageRequest.of(0, 10)).getContent();
        System.out.println("content1: " + JSONArray.toJSONString(content1));
    }

    /**
     * 多个匹配
     */
    @Test
    public void multipleQuery() {
//        QueryBuilder queryBuilder= QueryBuilders.termsQuery("price", 10d, 20d, 45d);
//        List<Product> list = productRepository.search(queryBuilder, PageRequest.of(0, 10)).getContent();
//        System.out.println("content: "+JSONArray.toJSONString(list));
        MultiMatchQueryBuilder price = QueryBuilders.multiMatchQuery(10d, "price");
        List<Product> content = productRepository.search(price, PageRequest.of(0, 10)).getContent();
        System.out.println("content: " + JSONArray.toJSONString(content));
    }

    /**
     * 模糊匹配
     */
    @Test
    public void fuzzyQuey() {
        //模糊匹配  具体变量
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("无印睡").field("name");
        List<Product> content = productRepository.search(queryBuilder, PageRequest.of(0, 10)).getContent();
        System.out.println("content: " + JSONArray.toJSONString(content));
            //1.常⽤的字符串查询
        QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//左右模糊
            //2.常⽤的⽤于推荐相似内容的查询
       // QueryBuilders.moreLikeThisQuery(new String[]{"fieldName"}).addLikeText("pipeidhua");//如果不指定filedName，则默认全部，常⽤在相似内容的推荐上
            //3.前缀查询，如果字段没分词，就匹配整个字段前缀
        QueryBuilders.prefixQuery("fieldName", "fieldValue");
           //4.fuzzy query:分词模糊查询，通过增加 fuzziness 模糊属性来查询，如能够匹配 hotelName 为 tel 前或后加⼀个字⺟的⽂档，fuzziness 的含义是检索的 term 前后增加或减少 n 个单词的匹配查询
        QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);
           //5.wildcard query:通配符查询，⽀持* 任意字符串；？任意⼀个字符
        QueryBuilders.wildcardQuery("fieldName", "ctr*");//前⾯是fieldname，后⾯是带匹配字符的字符串
        QueryBuilders.wildcardQuery("fieldName", "c?r?");

    }


}
