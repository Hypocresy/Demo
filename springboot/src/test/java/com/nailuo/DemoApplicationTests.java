package com.nailuo;


import com.nailuo.util.RSAUtils;

public class DemoApplicationTests {

    public static void main(String[] args) throws Exception {
      String pass ="Uotxv3kTEckhUUrvtOmerbpkGT3RzNqUWfFwZs18A3FoTo2lKU8i8DWwUoBQ/Y6SSa0KjowVeIKfrVRtp9SfN1gPLEGq68UIxtwoEIxw7NtU2PQjS11HXhi6Kr0S6Q/xeh9AkGe3A9nc/MpWI6TFfbYla0iafgI97lD4221m1Gw=";
              String  privage ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKsyVPantQuoEx38zz9IYvAY+g9rzwaOWwcRc3OeDZyLFTuwrxZU8kadNC6zHC1K2Sw3CLEhO8UasqRKjtMTpQsRSdewmzbJYTt6ZQHq2NLTknPpQNRF/FD+rP5Lxt2YhaXKd6Z0u5tfwBH2rXGA4xRPlVffnyuPZ9fXWy7zbnazAgMBAAECgYEAo+qXS2wcf0K4uze6wB7xDG79zxIssVxQcB4XaxZkdgT9mZ98pWdEarROqIA6dGt8MUeofOETN5Buh7wTsKrYlIHYZRIhCasrKUUYjf/QDGKxTjiUiNN0iVE0nnX7YIc/3Lsd0IhGEc6mc8jZtdBZ0JY5obWMpPzHIYL4GMNBtwECQQDu/sDH41ryMO6b2/tpYaUVz+3dR7Tcv5alMIzrYXYmbY+XPh7guR7aIdqyuRnOOhnXhpsGaKXsnzn7Mg7tfvHzAkEAt2Ckja8KEAHQiS5OHnOUX/YJwXNaNR4o6PPS6T7fnT08gzFB5654GL0KSSVYUVhJYMUVWSiTUkyZ71hLozrYQQJAED8DDdN3sDUBU1j92ykHFtd+nOct9T+U1v/Y5HtIk84oz6gqYy5SWZOpkTKh/XHXpd4BlcrxVIdyYpABkcVqXwJAfvInWHI5fBVVgPIOYGRQ7hGMdWBerXEAjyvj8VZ2RQsUbhnWbwXH3BBaI4L33fBJc/Vbrwo42ntvAVNVoV1mQQJBAIJbJTaDjQV0X8H6XHUeB3QzlU2hLVOv92LS7Xdw/h4fP/6SWEqPQEBKkQyWizdHGWnDg4PVxuvU2UoHJIetmJA=";
        String dataOnJava = RSAUtils.decryptDataOnJava(pass, privage);
        System.out.println(dataOnJava);



    }


}
