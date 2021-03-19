package com.nailuo.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class RSAUtils extends RsaUtil {

	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/** */
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** */
	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/** */
	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 * 
	 * @param data
	 *            待加签preSign
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return encryptBASE64(signature.sign());
	}

	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            待加签preSign
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(decryptBASE64(sign));
	}

	/** */
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/** */
	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/** */
	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/** */
	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * java端公钥加密
	 * 
	 * @throws Exception
	 */
	public static String encryptedDataOnJava(String data, String PUBLICKEY) throws Exception {
		data = encryptBASE64(encryptByPublicKey(data.getBytes(), PUBLICKEY));
		return data;
	}

	/**
	 * java端私钥解密
	 * 
	 * @throws Exception
	 */
	public static String decryptDataOnJava(String data, String PRIVATEKEY) throws Exception {
		String temp = "";
		byte[] rs = decryptBASE64(data);
		temp = new String(RSAUtils.decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8"); // 以utf-8的方式生成字符串
		return temp;
	}

	public static void main(String[] args) throws Exception {
		String str = "fsEQVJpNRVrrVPHeRenz1aAgg8/9jQt3Gmgcd24l2qJFGxkWH3jNe6spxqueT6K5WPsGnstFwQtAUWUx3qfICSCLDmTli339P0UQ/AjXylPMY+mBcdEntisH2sen0YcIkX7Uw5XrXgz3qvjGFWT6hRdIp/LX7WpQRewfnYgbFtA=";
		// String publicKey =
		// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSTVUxoKCloz/LVi+yw4IYlea20v0L8pknjCEmJBl9ycZZdjPEN7VuQoiluM88fW2Wf6heeMFAbbbSbRK8Dm+RFXLB1ehB4qFED3AY1oKSzCO5mZjLTrJ1Ed7uF5PQSFwncyc91GJv7A0VFtC1Lf24J2L/gRiyPoqzkvcFgNXlqwIDAQAB";
		// String privateKey =
		// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJNVTGgoKWjP8tWL7LDghiV5rbS/QvymSeMISYkGX3Jxll2M8Q3tW5CiKW4zzx9bZZ/qF54wUBtttJtErwOb5EVcsHV6EHioUQPcBjWgpLMI7mZmMtOsnUR3u4Xk9BIXCdzJz3UYm/sDRUW0LUt/bgnYv+BGLI+irOS9wWA1eWrAgMBAAECgYAIzDe0TUl7zG5Ypu0lXdZj7he6pMNsxYDqPOX9aixSQSD5Xj9MrGDvqXaYKJ3lsFE3vKN+UtqkKjTVQJPg1SlmQ6CAemmFZ6xtHT20Vu3pZlrXO9uncCJKZq7JE3C7UJwtf6KID/8t8hqxPQf8vPH6JCgNMFZK52ETnt6HsS3O4QJBANw8IrDODM9TiDNNDj48/jQDlSPp0kE2YIRowR5OTw+1oKbBuHXqDEvMetoSxyygFwn2k3xvLEpmEJ8KifIeh3ECQQCqD5Myvl5PqSTJ9duWwu2icDKTPykirb0OQvB7p6wi7CeU/D5V4UzfSQZYduFoaGgjUnsxc7jm9TeUntI7BIjbAkEAy76+gT8+zpeCy6Mf6ChZLBmcYisxHq+FvzmCX90me2wWge96DWxHj+BOT21L4lcAuXDqpRXcrb+a5OfFEr93oQJAfarmvm+4p6s5OVjJ/R1sl0XLyc25qxuyAhDrPqVH6cKS+WBw++tyb+m5m7O1m/7TPY7c5E08jMcWXGgEuIIAUwJBAItkiA5t5OX6FccRMwJXgDLckpz+giULb3qg8LwZj1twgM7hU479t6TYuiyn/aJdaADzN5X0qljDnPKYiZ+BDeI=";
		// Map<String,Object> keyMap = genKeyPair();
		// System.out.println("publicKey:"+getPublicKey(keyMap));
		// System.out.println("privateKey:"+getPrivateKey(keyMap));
		// System.out.println("--------");
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3ywV4WcGiRviDqq8ondSHRGWw8cpNGrHW8FbPfm8ZTzYu6qXumUlbSM679d0lGz8Ihv74rITmruW1RCMk24PTtlhowzMG8Q4ubDW/f9cL89gL2YnVYL26exg9MKHpuyRhxNkYWWBPf+ULHe0S3YwSWvuQ+imaECHXA+ykaFDzmQIDAQAB";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALfLBXhZwaJG+IOqryid1IdEZbDxyk0asdbwVs9+bxlPNi7qpe6ZSVtIzrv13SUbPwiG/vishOau5bVEIyTbg9O2WGjDMwbxDi5sNb9/1wvz2AvZidVgvbp7GD0woem7JGHE2RhZYE9/5Qsd7RLdjBJa+5D6KZoQIdcD7KRoUPOZAgMBAAECgYB4skyvUlJE3VKKNOa+R5ewFQyq/L4bkHJFcqOvs5oGBuzQNqwtWmDAH7oT2UwW+COW2ABNEg5c53JWbZ6er8GLgYd/e4DE8iB87jB5e8NPlpkMAdhTN5jdGlj0yKjFL8ue4P7KL/kFiV4h8PhF2S9vwBFyRaom5RL/7QsJvNECOQJBAPejpO+G+QhNbwl9752Qu/+FmUdbUTQ5w3OJru8iiCYbXM5LsN/dCpt4HefsTMDCocrUrphils1eCnNHKhY8nLsCQQC9/41PsPP4NbUUScWObHTMW6nodvu1w7bE/uxGoKscYGIMnEF/IfJyjfSOcKe+ds0h5naClddhHQRAirG5K3W7AkEAtgUPl4vDAipAYl6xwEQBGzjsyDlDv7pxgjwCW/0qy6+zEGMsN++1+z5X5P+YDttApuCbEKDurTbhQYheVAfrPwJASrukJ75gmDb33N0Pme/LGT7M3CVA6MPsthvXcAhuhaDbsD4d/RWrOafgk854DJDKKxVtc/vgVdCGBaUINt+DVQJACWyuSCg612G13L6bYW7Neb43z57YVdHS9Qcx60wr5xArO2pssLSyXbEBXybOJK1CMIHlN20oZ7o+zAgKlxfGJg==";
		String date1 = encryptedDataOnJava("55555", publicKey);// 公钥加密密钥A，此处为55555
		// System.out.println(date1);
		String date2 = decryptDataOnJava(date1, privateKey);
		System.out.println(date2);
		System.out.println("----");

	}

}