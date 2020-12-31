package com.nailuo;


import com.nailuo.util.SM3Util;

public class DemoApplicationTests {

    public static void main(String[] args) {
        String msg = SM3Util.getSignatureBySM3("requestRefId=SJSREQ_201601010809108632A&secretId=10050", "ZUNvcmN3cW14MXM5YTJ4bXdqODkxYXNk");
        System.out.println(msg);

    }


}
