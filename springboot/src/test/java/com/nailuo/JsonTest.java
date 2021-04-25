package com.nailuo;


import com.nailuo.util.RSAUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * @author hy
 * @blame Development Group
 * @date 2020/12/18 15:03
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        /*  String str1 = "20210122214759700";
          String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrMlT2p7ULqBMd/M8/SGLwGPoPa88GjlsHEXNzng2cixU7sK8WVPJGnTQusxwtStksNwixITvFGrKkSo7TE6ULEUnXsJs2yWE7emUB6tjS05Jz6UDURfxQ/qz+S8bdmIWlynemdLubX8AR9q1xgOMUT5VX358rj2fX11su8252swIDAQAB";
        String encryptedDataOnJava = RSAUtils.encryptedDataOnJava(str1, public_key);
        System.out.println("加密： "+encryptedDataOnJava);
        String  privage ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKsyVPantQuoEx38zz9IYvAY+g9rzwaOWwcRc3OeDZyLFTuwrxZU8kadNC6zHC1K2Sw3CLEhO8UasqRKjtMTpQsRSdewmzbJYTt6ZQHq2NLTknPpQNRF/FD+rP5Lxt2YhaXKd6Z0u5tfwBH2rXGA4xRPlVffnyuPZ9fXWy7zbnazAgMBAAECgYEAo+qXS2wcf0K4uze6wB7xDG79zxIssVxQcB4XaxZkdgT9mZ98pWdEarROqIA6dGt8MUeofOETN5Buh7wTsKrYlIHYZRIhCasrKUUYjf/QDGKxTjiUiNN0iVE0nnX7YIc/3Lsd0IhGEc6mc8jZtdBZ0JY5obWMpPzHIYL4GMNBtwECQQDu/sDH41ryMO6b2/tpYaUVz+3dR7Tcv5alMIzrYXYmbY+XPh7guR7aIdqyuRnOOhnXhpsGaKXsnzn7Mg7tfvHzAkEAt2Ckja8KEAHQiS5OHnOUX/YJwXNaNR4o6PPS6T7fnT08gzFB5654GL0KSSVYUVhJYMUVWSiTUkyZ71hLozrYQQJAED8DDdN3sDUBU1j92ykHFtd+nOct9T+U1v/Y5HtIk84oz6gqYy5SWZOpkTKh/XHXpd4BlcrxVIdyYpABkcVqXwJAfvInWHI5fBVVgPIOYGRQ7hGMdWBerXEAjyvj8VZ2RQsUbhnWbwXH3BBaI4L33fBJc/Vbrwo42ntvAVNVoV1mQQJBAIJbJTaDjQV0X8H6XHUeB3QzlU2hLVOv92LS7Xdw/h4fP/6SWEqPQEBKkQyWizdHGWnDg4PVxuvU2UoHJIetmJA=";
        String dataOnJava = RSAUtils.decryptDataOnJava(encryptedDataOnJava, privage);
        System.out.println("解密： "+dataOnJava);*/

        Long time = 1617947845487L;
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        System.out.println(instance.getTime());

    }
}
