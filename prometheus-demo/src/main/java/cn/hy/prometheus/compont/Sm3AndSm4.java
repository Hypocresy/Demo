package cn.hy.prometheus.compont;


import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;


/**
 * @author hy
 * @blame Development Group
 * @date 2020/11/20 17:52
 * @since 0.0.1
 * 即使你忘记了我，我也不会遗忘你
 */
public class Sm3AndSm4 {
    private static String secreKey ="ZUNvcmN3cW14MXM5YTJ4bXdq0DkxYXNk";
    private  static  String message = "requestRefId=SJSREQ_201601010809108632A&secretId=10050";
    private  static  String body ="{\"param\":[{\"begin\":0,\"end\":10000}]}";
    private  static  String begin  ="0";
    private  static  String end = "10000";
    private  static  String reponse ="302ec70d1aa55c905473d061270d34289e3e786da671f8ebc525a4eb84d9402e6dd371ce622550e7a668822ba264d71004442f4b1fa9de27ec53b4034c48a6b9f3dcd532b0ce2cdc56b79ea3ea9ffda7b4297d2bd6f04e95a1322eedec4e54ed57d4464a7d26da2dbc705dc629e5d87c7bb474749c514cec4eaa817cfda2ce5a7665b62ce7e98b42e3196cc074b8e8f2c367fa6c3784e6c612fa277e10813503e3b16b1c070213bd7e3c562181168b2b5a467b67e45d5e2ad24947bf112ac057f32c87574a5c52e956adc883aa135e053c18bcb5462441c6102707369a7ca330b71e8303a54de78e6c6217bb79121cb622189f740de1aa334c5f36304997983394703219905d14d1dd80ea31299b2f7423d1d7cf639d9740df09a4a0f762261f6b9a84e2d0cf9574d4fc665a6fe09e08883f20c3cf2c790e026094cfb355f92f43c56ff71f27773e7b8342f09a088ba84683cb0866b3232a9afda483464b20e47fd7086361cd6761bcfad344e91e84eef7c8dfe2aa3c68095bd4f656ee3d394c92f2009086992712c83c61d68e604f8684dfca9fb9170c48e98608f055cc346d78a3be027a5180319e842b8e4be398088ddb1f69d7e28fa2107bf0af4fbd5828dfe496b6450bf7a63b8958b5524f35cc6e21c6c1ae53450628f45ef043045b40bcedbd1fe9ba2ab835d3c7982f464606084eb7c17da06a6e006841289cf8f26b594c75ffeb4f4f09379bf2d15fb462708939876f823c236e95f47878907dd8ad13e4d54322976864dc5d07b4a744d34ee9faa953373d88dec586ba0c8e12eec4b2db6b0b8f43dd43f7cc43f4fb0c87ec79a4bec6dc2fae270b745471e1e1c9e3547abc95f256be311ce1bc9bbe376bbf7de7dcf65c56545ab580e3ed6df1f515f1708932f1df092243b63738b40143c891ab695bfe64b2e464e0075312345ca5a9a2a8a396a7007e6dd336941b6f8c5b228b225fdffde3c326e02d3a873250de8f739049fb0d8c81dd3e188334953073521464bf8f943688213b52a38af99858896cd204794ab3897632c8759efdf494d0321cd3b68e5cf4723ba13b9ad751361430fbc981b6a7a6462e1eb29d7a05f7a4d04a7af0b5340f1b934c7dbfcb5a89da358e13aaf41815a7283623858879663f7d8d67b156af64d78a4ee480178226bb9efb5458bfc7db5b06475848645538fc43d768e4678e751498bb68c7a571cb77c42ed17e1eee1a751ae450e40882664e4bd6efc96252a293670da5ec059f1ba00219472d266993105a3b7711db48dcd3580e0ce9dd716960c841f63dbf42752078ffc0e7e3da0d999c60aa350fa140a869ec3f4d6986876be63fc9aecdf2ead3c9dda43d4afbfc0d529281d9189a27eef5cda5f50cc6d953b0b866ec080b10d084fb013675d78fed17b2c2b29207e3e0290ca389cd8a15e080a0ce8dae76f9fdb44b0795811466c1c0362359224e737bb474749c514cec4eaa817cfda2ce5a7665b62ce7e98b42e3196cc074b8e8f2c367fa6c3784e6c612fa277e10813503e3b16b1c070213bd7e3c562181168b2bc4804238f39a5a23e269e8f8b0269f40f32c87574a5c52e956adc883aa135e053c18bcb5462441c6102707369a7ca330b71e8303a54de78e6c6217bb79121cb622189f740de1aa334c5f36304997983394703219905d14d1dd80ea31299b2f7423d1d7cf639d9740df09a4a0f762261f6b9a84e2d0cf9574d4fc665a6fe09e08883f20c3cf2c790e026094cfb355f92f43c56ff71f27773e7b8342f09a088ba84683cb0866b3232a9afda483464b20e47fd7086361cd6761bcfad344e91e84eef7c8dfe2aa3c68095bd4f656ee3d394ce75bff896f7e9ddf188498eb3cc592be84dfca9fb9170c48e98608f055cc346d78a3be027a5180319e842b8e4be398088ddb1f69d7e28fa2107bf0af4fbd5828dfe496b6450bf7a63b8958b5524f35cc6e21c6c1ae53450628f45ef043045b40bcedbd1fe9ba2ab835d3c7982f464606084eb7c17da06a6e006841289cf8f26b594c75ffeb4f4f09379bf2d15fb462708939876f823c236e95f47878907dd8ad13e4d54322976864dc5d07b4a744d34ee9faa953373d88dec586ba0c8e12eec4b2db6b0b8f43dd43f7cc43f4fb0c87ec79a4bec6dc2fae270b745471e1e1c9e3547abc95f256be311ce1bc9bbe376bbf7de7dcf65c56545ab580e3ed6df1f515f1708932f1df092243b63738b40143c891ab695bfe64b2e464e0075312345ca5a9a2a8a396a7007e6dd336941b6f8c5b228b225fdffde3c326e02d3a873250de8f739049fb0d8c81dd3e188334953073521464bf8f943688213b52a38af99858896cd204794ab3897632c8759efdf49492d775caea0eee76373d5bde5dde4e0fc6dfce5d45265c0847324c2b390755d4a4d04a7af0b5340f1b934c7dbfcb5a89da358e13aaf41815a7283623858879663f7d8d67b156af64d78a4ee480178226bb9efb5458bfc7db5b06475848645538fc43d768e4678e751498bb68c7a571cb77c42ed17e1eee1a751ae450e40882664e4bd6efc96252a293670da5ec059f1ba00219472d266993105a3b7711db48dcd3580e0ce9dd716960c841f63dbf42752078ffc0e7e3da0d999c60aa350fa140ea6763dcb89bd931d1481cb5aeace954";
    public static void main(String[] args) throws Exception {
        byte[]  key= secreKey.getBytes("utf-8");
        byte[] bytes = message.getBytes("utf-8");
        byte[] hmac = hmac(key, bytes);
        String hexString = ByteUtils.toHexString(hmac);
//        System.out.println(hexString.toUpperCase());
//        System.out.println(hexString);
        String signatureBySM3 = getSignatureBySM3(message,secreKey);
        System.out.println(signatureBySM3);
//        String beginAS = SM4Util.encode(begin, secreKey);
//        String encodeAS = SM4Util.encode(end, secreKey);
        String param = SM4Util.encode(body,secreKey);
        System.out.println("param: "+param);
        String res = SM4Util.decode(reponse, secreKey);
        System.out.println("response: "+res);
        String s = res.replaceAll("\\\\", "");
        System.out.println(s);
//        JSONObject jsonObject = JSONObject.parseObject(s);
//        System.out.println("josnObject: "+JSONObject.toJSONString(jsonObject) );
//        System.out.println("begin: "+beginAS+" end: "+encodeAS);
    }


    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }


    public static String getSignatureBySM3(String message, String secret) {
        String signature = null;
        KeyParameter keyParameter;
        try {
            keyParameter = new KeyParameter(secret.getBytes("UTF-8"));
            SM3Digest digest = new SM3Digest();
            HMac mac = new HMac(digest);
            mac.init(keyParameter);
            mac.update(message.getBytes("UTF-8"), 0, message.length());
            byte[] byteSM3 = new byte[mac.getMacSize()];
            mac.doFinal(byteSM3, 0);
            signature = new BASE64Encoder().encode(byteSM3);
        } catch (UnsupportedEncodingException e) {
            System.out.println("getSignatureBySM3 error :");
            e.printStackTrace();
        }
        return signature;
    }
}
