package com.shanghai.sdkinterface.controller;

import cn.hutool.json.JSONObject;
import com.shanghai.sdkinterface.util.*;
import io.lettuce.core.StrAlgoArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.crypto.Cipher.PUBLIC_KEY;

/**
 * @author Administrator
 * @title: MobileVerification
 * @projectName moplogin
 * @description: TODO
 * @date 2021/1/2010:06
 */
@RestController
@RequestMapping("/openapi/rs")
public class MobileVerification {
    private static final Logger log = LoggerFactory.getLogger(MobileVerification.class);


    @Value("${app.internet.tokenvalidate.url}")
    private String mobiletokenvalidate;



    /**

     *@描述

     *@参数

     *@返回值

     *@创建人 HouYongKuan

     *@创建时间 2021/1/20

     *@修改人和其它信息

     */
    @RequestMapping("/tokenValidate")
    public String mobilePhoneVerification(@RequestBody JSONObject jo, HttpServletRequest request) throws Exception {
        //TODO 手机号码校验平台公钥
        String keyPublic="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj1OVadc+4LI82GkBjA9Q/pNlsayvqNOXiMQU8mvegZYxfb7r8cM2CkFtXA8xYGI/4tE3f7qUljeF6aUa/ngNhovv33WTP5nF5VkhBgLPUchZ4X32HvkNYVuduceadFvfq5G9s13GK1F+I3H8l0Jz6584nGnFO7jpzFkfXB9rPVwIDAQAB";
        //TODO 公私钥对
        String publickeyValue = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqx3XLN0vSrZKqlO9Od23YDPNUPhWgJxDQgbvOr3CxBCKyPx33/aL4cssoclE4PwdwfUWeX8ONGJypHN1GsEHMfc8/saDjJwUqVGPYFK7wtELGUhzR4IJmpMiXE7tPxznQcQal5wbthY90V+gNNl7PLRHZth8TVwaEcetSDJOfkwIDAQAB";;
        String privatekeyValue = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKrHdcs3S9KtkqqU7053bdgM81Q+FaAnENCBu86vcLEEIrI/Hff9ovhyyyhyUTg/B3B9RZ5fw40YnKkc3UawQcx9zz+xoOMnBSpUY9gUrvC0QsZSHNHggmakyJcTu0/HOdBxBqXnBu2Fj3RX6A02Xs8tEdm2HxNXBoRx61IMk5+TAgMBAAECgYEApgatZLfIVfP6ry2FjNIcjrrgYJjO61ivRxWo4KG8u8radFFaMDp7km//Q74HadgUwm53HSXadbRksR/nmw5FmBGGxH0ns62dnSk/t1dAuhjrmP+DllJNg3geL1fiTdfcP+qR9Gl7/Ky1s0zDJke5nL5B2oXnjpmDCt5NVU+GvhkCQQDfwtu2nNrpFChrhoXs3KQqwx/w/lYpCPi7low11jCSuNbn1i208PlbtfCSTlxS3Ue1Ygr7an1JHonXbSTYPd/XAkEAw2Jtpe/9sYGqQMqUGXHaYvmgDv8YXRTmQY2XVRhO+5Fv/ee+Uwo5esJNd2yKUwKroE53yM3n2lUTGp5e2P02pQJBAIbiXbPdJhEut5bpxr2b29JzQUPy7VlbMSVTgT3K2gj4J4QllfPm90oTiOuJFRpWja2HZntcHB4BrLIR9w3rbo0CQHuNq67qY3Azgdk1AZBRmftKxbeWAu8hvzWm9xK9q3yUDNDwEj/q0ExbwKcLgTFbF/LojAD0jHBZqhoEGhB3l20CQQC7E2Xj+YwFmi9Ke0xCynHiNitstV+Qy68h1io1lsdJtd733Ejz/NaeS7V9lBSwswtP147kwsTJStxEYROIOS3T";

        String timestamp = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date());
        timestamp= timestamp.replace("/","").replace("-","").replace(":","");
        String uuid = UUID.randomUUID().toString().replace("-","");
        String msgId = uuid;
        String appId = "300012017817";
        String token = "STsid0000001611142603079xFBvhr5wioRfAI0wABCux8DIWUkf9csp";

        StringBuilder sb = new StringBuilder();
        String phoneNumber = "13265580307";
        String appKey = "27BDE9F584B69D67C75038BE70BCA8CE";
        sb.append(phoneNumber);
        sb.append(appKey);
        sb.append(timestamp);
        //TODO keytype = "1“ rsa公钥加密手机号码
        String  phoneEncode =RSAUtils.encryptedDataOnJava(sb.toString(),keyPublic);
        log.info("加密手机号为:"+phoneEncode);
        //TODO /* 加密内容为appId + msgId + phoneNum + timestamp + token +version。签名算法MD5withRSA*/
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append(appId);
        signBuilder.append(msgId);
        signBuilder.append(phoneEncode);
        signBuilder.append(timestamp);
        signBuilder.append(token);
        signBuilder.append("1.0");
        String sign=RSAUtils.sign(signBuilder.toString().getBytes(),privatekeyValue);
        log.info("md5withrsa加密 sign="+sign);
        JSONObject headJson = new JSONObject();
        headJson.put("appId",appId);
        headJson.put("msgId",uuid);
        headJson.put("version","1.0");
        headJson.put("timestamp",timestamp);
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("openType","0");
        bodyJson.put("token",token);
        bodyJson.put("sign",sign);
        bodyJson.put("phoneNum",phoneEncode);
        bodyJson.put("requesterType","0");
        bodyJson.put("keyType","1");
        JSONObject joRequest = new JSONObject();
        joRequest.put("header",headJson);
        joRequest.put("body",bodyJson);
        String responseResult = OkHttpClientUtil.sendPost(mobiletokenvalidate, "application/json", joRequest.toString());
        System.out.println("responseResult"+responseResult);
        return responseResult;
    }
}
