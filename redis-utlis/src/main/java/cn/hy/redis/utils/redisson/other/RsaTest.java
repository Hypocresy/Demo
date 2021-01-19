package cn.hy.redis.utils.redisson.other;

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
public class RsaTest {
    private  static final   String public_key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrMlT2p7ULqBMd/M8/SGLwGPoPa88GjlsHEXNzng2cixU7sK8WVPJGnTQusxwtStksNwixITvFGrKkSo7TE6ULEUnXsJs2yWE7emUB6tjS05Jz6UDURfxQ/qz+S8bdmIWlynemdLubX8AR9q1xgOMUT5VX358rj2fX11su8252swIDAQAB";
    private static  final String private_key ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKsyVPantQuoEx38zz9IYvAY+g9rzwaOWwcRc3OeDZyLFTuwrxZU8kadNC6zHC1K2Sw3CLEhO8UasqRKjtMTpQsRSdewmzbJYTt6ZQHq2NLTknPpQNRF/FD+rP5Lxt2YhaXKd6Z0u5tfwBH2rXGA4xRPlVffnyuPZ9fXWy7zbnazAgMBAAECgYEAo+qXS2wcf0K4uze6wB7xDG79zxIssVxQcB4XaxZkdgT9mZ98pWdEarROqIA6dGt8MUeofOETN5Buh7wTsKrYlIHYZRIhCasrKUUYjf/QDGKxTjiUiNN0iVE0nnX7YIc/3Lsd0IhGEc6mc8jZtdBZ0JY5obWMpPzHIYL4GMNBtwECQQDu/sDH41ryMO6b2/tpYaUVz+3dR7Tcv5alMIzrYXYmbY+XPh7guR7aIdqyuRnOOhnXhpsGaKXsnzn7Mg7tfvHzAkEAt2Ckja8KEAHQiS5OHnOUX/YJwXNaNR4o6PPS6T7fnT08gzFB5654GL0KSSVYUVhJYMUVWSiTUkyZ71hLozrYQQJAED8DDdN3sDUBU1j92ykHFtd+nOct9T+U1v/Y5HtIk84oz6gqYy5SWZOpkTKh/XHXpd4BlcrxVIdyYpABkcVqXwJAfvInWHI5fBVVgPIOYGRQ7hGMdWBerXEAjyvj8VZ2RQsUbhnWbwXH3BBaI4L33fBJc/Vbrwo42ntvAVNVoV1mQQJBAIJbJTaDjQV0X8H6XHUeB3QzlU2hLVOv92LS7Xdw/h4fP/6SWEqPQEBKkQyWizdHGWnDg4PVxuvU2UoHJIetmJA=";
    public static void main(String[] args) throws Exception {
        String phone ="15650580957";
        String msgid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(msgid);
        String timestamp = "20210115150233121";
        String token = "sadsadadddYYYYsddddadsdas";
        String version = "2.0";
        String appkey ="0CCA90098657CE817AE7EDEA766D0E7D";
        String appid = "300011986118";
        RSAPublicKey publicKeyBykey = TestRSA.getPublicKeyBykey(public_key);
        String phonen = TestRSA.publicKeyEncrypt((phone + appkey + timestamp), publicKeyBykey);
        System.out.println(phonen);
        RSAPrivateKey rsaPrivateKey = TestRSA.getprivateKeyBykey(private_key);
        String sgin = TestRSA.generalSign((appid+msgid+phonen+timestamp+token+version).getBytes("UTF-8"),rsaPrivateKey);
        System.out.println(sgin);
        boolean b = TestRSA.signVerify(appid + msgid + phonen + timestamp + token + version, sgin, publicKeyBykey);
        System.out.println(appid + msgid + phonen + timestamp + token + version);
        System.out.println(b);

    }

}
