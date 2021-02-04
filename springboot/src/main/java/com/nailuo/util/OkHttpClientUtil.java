package com.shanghai.sdkinterface.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hachel on 2018/1/10
 */
public class OkHttpClientUtil {


    private static Logger logger = LoggerFactory.getLogger(OkHttpClientUtil.class);

    private static OkHttpClient okClient = new OkHttpClient().newBuilder()
            .connectTimeout(150000, TimeUnit.MILLISECONDS)//设置超时时间
            .readTimeout(3000000, TimeUnit.MILLISECONDS)//设置读取超时时间
            .writeTimeout(3000000, TimeUnit.MILLISECONDS)//设置写入超时时间
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(50, 10, TimeUnit.MINUTES))
            .build();

    /**
     * 发送post请求
     * @param url
     * @param content
     * @param contentType
     * @return
     */
    public static String sendPost(String url, String contentType, String content){
        String respStr = null;
        Response response = null;
        long start = System.currentTimeMillis();
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), content);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            response = okClient.newCall(request).execute();
            if (response.isSuccessful()){
                respStr = response.body().string();
            }else{
                logger.error("请求url:{}错误，错误信息:{}", url, response.body().string());
            }
        }catch (SocketTimeoutException se){
            logger.error("业务标志url={},content={},请求超时{}ms", url, content, (System.currentTimeMillis() - start));
        }catch (Exception e){
            logger.error("sendPost error " + url, e);
        }finally {
            logger.info("调用requestUrl={}结束，请求参数：{}，响应结果：{}",url,content,respStr);
            try {
                if (response != null){
                    response.close();
                }
            }catch (Exception e){
                logger.error("response close error", e);
            }
        }
        return respStr;
    }

}
