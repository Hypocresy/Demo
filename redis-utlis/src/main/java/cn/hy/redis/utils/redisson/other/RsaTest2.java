package cn.hy.redis.utils.redisson.other;

import cn.hy.redis.utils.redisson.other.util.HMAC_SHA;
import cn.hy.redis.utils.redisson.other.util.SHA256Util;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/15 15:42
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class RsaTest2 {
  public static void main(String[] args) throws Exception {
        String phone ="15650580957";
        String msgid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(msgid);
        String timestamp = "20210115150233121";
        String token = "sadsadadddYYYYsddddadsdas";
        String version = "2.0";
        String appkey ="0CCA90098657CE817AE7EDEA766D0E7D";
        String appid = "300011986118";
        String phonen =   SHA256Util.getSHA256String(phone+appkey+timestamp).toUpperCase();
        String sgina =appid + msgid + phonen + timestamp + token + version;
        HMAC_SHA.HMACSHA256(sgina.getBytes("UTF-8"),appkey.getBytes("UTF-8"));
    }

}
