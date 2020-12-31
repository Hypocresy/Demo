package com.nailuo.util;

import com.huawei.crypto.provider.SMS4KeySpec;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.security.Security;

/**
 * sm4工具类
 * 秘钥不得小于16位
 * @author hWX588043
 *
 */
public class SM4Util {

	static {
		Security.addProvider(new com.huawei.crypto.provider.HWJCE());
	}

    public static String decode(String encrypted, String secretKey) throws Exception {
        // 加密结果转码
        byte[] data = Hex.decodeHex(encrypted.toCharArray());
        // 用户密钥处理
        SecretKey key = getKey(secretKey);

        // 解密
        Cipher cipher = Cipher.getInstance("SMS4");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] reclaimedBytes = cipher.doFinal(data);

        // 将解密结果输出
        return new String(reclaimedBytes, "utf-8");
    }

    /**
     * @param source    待加密字符串
     * @param secretKey 加密秘钥
     * @return 加密结果
     */
    public static String encode(String source, String secretKey)
            throws Exception {
        // 字符串转byte[]
        byte[] data = source.getBytes("utf-8");
        // 密钥处理
        SecretKey key = getKey(secretKey);

        // 加密
        Cipher cipher = Cipher.getInstance("SMS4");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data);

        // 将加密结果转16进制输出
        return Hex.encodeHexString(encryptedBytes);
    }

    /**
     * 对secretKey进行加密
     *
     * @param secretKey 密钥
     * @throws Exception
     */
    private static SecretKey getKey(String secretKey) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(secretKey);
        SMS4KeySpec sms4KeySpec = new SMS4KeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("SMS4");
        return keyFactory.generateSecret(sms4KeySpec);
    }

}
