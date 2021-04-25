package com.nailuo.contorller;


import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.nailuo.util.RateLimiterAn;
import com.nailuo.util.SM4Util;
import com.nailuo.vo.HttpRequest;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/26 11:54
 * @since 0.0.1
 *
 */
@RestController
public class WebContorller {
    @Autowired
   private Environment environment;
   @Autowired
    private HttpRequest requestEntyity;
   @Autowired
   private RestTemplate restTemplate;

   @RequestMapping("/openapi/queryData/{api}")
   @RateLimiterAn(value = 50,timeout = 60)
    public Object  res(@RequestBody JSONObject object, @PathVariable("api") String api) throws Exception {
       String param = JSONObject.toJSONString(object);
       //获取加密 密匙
       String secreKey = environment.getProperty("secreKey");
       String request = SM4Util.encode(param, secreKey);
       //设置请求头
       requestEntyity.init();
       HashMap<String, Object> entity = requestEntyity.getEntity();
       entity.put("request",request);
       //拼接请求路径
       String url = environment.getProperty("target.url");
       url = url+api;
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
       JSONObject jsonObj = new JSONObject(entity);
       System.out.println("jsonObj: "+JSONObject.toJSONString(jsonObj));
       HttpEntity<String> entitys = new HttpEntity<>(JSONObject.toJSONString(jsonObj), headers);
       ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entitys, String.class);
       String body = responseEntity.getBody();
       HashMap hashMap = JSONArray.parseObject(body, HashMap.class);
       System.out.println(JSONObject.toJSONString(hashMap));
      JSONObject head = JSONObject.parseObject(hashMap.get("head").toString());
      if(!"0000".equals(head.getString("responseCode"))){
         System.out.println("失败");
         return head;
      }
      String response = hashMap.get("response").toString();
      System.out.println("response:"+response);
       String decode = SM4Util.decode(response, secreKey);

       return decode;
    }


}
