package cn.hy.es.controller;

import com.alibaba.fastjson.JSONArray;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/2/3 13:42
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
@Component
public class CoreController {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 创建索引
     */

    public String createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("hy_craete_index1");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("respones: "+ JSONArray.toJSONString(createIndexResponse));
        return "success";
    }

    /**
     * 删除索引
     * @return
     * @throws IOException
     */
    public String deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hy_craete_index");
        AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(JSONArray.toJSONString(delete));
        return "success";
    }



}
