package cn.hy.redis.utils.redisson.other;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 
 * @author ct
 *
 */
public class TestRSA {
	// 加解密算法
	public static final String KEY_ALGORITHM = "RSA";
	// 签名算法 MD5withRSA SHA256withRSA SHA1withRSA NONEwithRSA
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	private String privateKeyStr;
	private String publicKeyStr;
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	/**
	 * Description：公钥加密
	 * 
	 * @param encodeData
	 * @return
	 * @return String
	 * @author 拜力文
	 **/
	public String publicKeyEncrypt(String sourceData) {
		Cipher cipher = null;
		try {
			byte[] data = sourceData.getBytes("UTF-8");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
			byte[] output = cipher.doFinal(data);
			return byte2hex(output);// byte2hex(output);
		} catch (Exception e) {
		}
		return null;
	}



	/**
	 * Description：私钥解密
	 * 
	 * @param encodeData
	 *            需要解密的数据 hex
	 * @return 解密后的数据
	 * @author 拜力文
	 */
	public String privateKeyDecrypt(String encodeData) {
		Cipher cipher = null;
		try {
			byte[] data = hexStr2byte(encodeData);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
			byte[] output = cipher.doFinal(data);
			return new String(output, "UTF-8");// byte2hex(output);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Description：签名验证
	 * 
	 * @param sourceData
	 * @param signData
	 * @return
	 * @return boolean
	 * @author 拜力文：
	 **/
	public boolean signVerify(String sourceData, String signData) {
		Signature signature;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(this.publicKey);
			byte[] srcData = sourceData.getBytes("UTF-8");
			signature.update(srcData);
			byte[] hexbyte = hexStr2byte(signData);
			if (hexbyte == null) {
				return false;
			}
			return signature.verify(hexbyte);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Description：生成签名
	 * 
	 * @param data
	 * @return
	 * @return String
	 * @author 拜力文：
	 **/
	public String generalSign(byte[] data) {
		Signature signature;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(this.privateKey);
			signature.update(data);
			return byte2hex(signature.sign());
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Description：生成签名
	 * 
	 * @param data
	 * @return
	 * @return String
	 * @author 拜力文：
	 **/
	public static String generalSign(byte[] data, RSAPrivateKey privateKey) {
		Signature signature;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey);
			signature.update(data);
			return byte2hex(signature.sign());
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Description：生成公私钥对
	 * 
	 * @return
	 * @throws Exception
	 * @return RSA
	 * @author name：拜力文
	 *         <p>
	 * 		============================================
	 *         </p>
	 *         Modified No： Modified By： Modified Date： Modified Description:
	 *         <p>
	 * 		============================================
	 *         </p>
	 **/
	public static TestRSA genKeyPair() throws Exception {

		KeyPairGenerator keyPairGen = null;
		keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		String privateKey64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		String publicKey64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		TestRSA rsa = new TestRSA();
		rsa.setPrivateKey(privateKey);
		rsa.setPublicKey(publicKey);
		rsa.setPrivateKeyStr(privateKey64);
		rsa.setPublicKeyStr(publicKey64);
		return rsa;

	}

	public String getPrivateKeyStr() {
		return privateKeyStr;
	}

	public void setPrivateKeyStr(String privateKeyStr) {
		this.privateKeyStr = privateKeyStr;
	}

	public String getPublicKeyStr() {
		return publicKeyStr;
	}

	public void setPublicKeyStr(String publicKeyStr) {
		this.publicKeyStr = publicKeyStr;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * 
	 * @param sourceData
	 * @param pKey
	 * @return
	 */
	public static String publicKeyEncrypt(String sourceData, RSAPublicKey pKey) {
		Cipher cipher = null;
		try {
			byte[] data = sourceData.getBytes("UTF-8");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pKey);
			byte[] output = cipher.doFinal(data);
			return byte2hex(output);// byte2hex(output);
		} catch (Exception e) {
			System.out.println("加密异常："+e);
		}
		return null;
	}



	public static String prilicKeyEncrypt(String sourceData, PrivateKey pKey) {
		Cipher cipher = null;
		try {
			byte[] data = sourceData.getBytes("UTF-8");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pKey);
			byte[] output = cipher.doFinal(data);
			return byte2hex(output);// byte2hex(output);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Description：私钥解密
	 * 
	 * @param encodeData
	 *            需要解密的数据 hex
	 * @return 解密后的数据
	 * @author 拜力文
	 */
	public static String privateKeyDecrypt(String encodeData, PrivateKey privatekey) {
		Cipher cipher = null;
		try {
			byte[] data = hexStr2byte(encodeData);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privatekey);
			byte[] output = cipher.doFinal(data);
			return new String(output, "UTF-8");// byte2hex(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String pubKeyDecrypt(String encodeData, PublicKey privatekey) {
		Cipher cipher = null;
		try {
			byte[] data = hexStr2byte(encodeData);
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privatekey);
			byte[] output = cipher.doFinal(data);
			return new String(output, "UTF-8");// byte2hex(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 生产RSA公钥
	 * 
	 * @param publickey
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKeyBykey(String publickey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		byte[] publicKeyByte = Base64.getDecoder().decode(publickey);
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyByte);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		return publicKey;
	}

	/**
	 * 生产RSA私钥
	 * 
	 * @param publickey
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey getprivateKeyBykey(String privatekey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		byte[] privateKeyByte = Base64.getDecoder().decode(privatekey);
		PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(priKeySpec);
		return privateKey;
	}

	/**
	 * Description：签名验证
	 * 
	 * @param sourceData
	 * @param signData
	 * @return
	 * @return boolean
	 * @author 拜力文：
	 **/
	public static boolean signVerify(String sourceData, String signData, RSAPublicKey publicKey) {
		Signature signature;
		try {
			signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicKey);
			byte[] srcData = sourceData.getBytes("UTF-8");
			signature.update(srcData);
			byte[] hexbyte = hexStr2byte(signData);
			if (hexbyte == null) {
				return false;
			}
			return signature.verify(hexbyte);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Description：将二进制转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 * @return String
	 * @author name：
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * Description：将十六进制的字符串转换成字节数据
	 * 
	 * @param strhex
	 * @return
	 * @return byte[]
	 * @author name：
	 */
	public static byte[] hexStr2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	public static String newString(byte[] bytes, String charsetName) {
		if (bytes == null) {
			return null;
		}
		try {
			return new String(bytes, charsetName);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIC36ljhxVqx1DgpUC8ZRhpScOs9"
				+ "X3FESzSjcvLVv2SXpwuMEHJIj7RpByk7rJ4FVHfXl52cotMdDVWi7BRQQz9xhIxyJG/H4zeeoDQH"
				+ "jg7PHYFDTVdsN/DRHDv1tipOBny0IrrGWW677Sid6Z/sMeUpKPL/8+eWh7JwO1arUn3hAgMBAAEC"
				+ "gYAOzJZ+F58oOU/sERvt/lroBdiDw2+oxzBaYfyCXP7/YsxK8JSnfx4+oOC45eqH1JcMnFYLQgoa"
				+ "ebmhwfSgtUW15+sdBReIaciZgS/p5GNk+j7pxgP4N9AJQiXGHxG235pibBAEVH92qnBHgIpN40Hz"
				+ "a1CWFhgTw1GOaFMPcz8qvQJBAOHGkbh4cn7bNxhslz0nvARjAMyekZdU4QCyQCCi0J3SfvxvzDRr"
				+ "vQVqun5AxUHTUNWhlRY3yub7DnYsFkqx3mcCQQCR8ybrZGMqNzeaxq/Fs5HTzbJosZ5k7nDfs5kS"
				+ "rW2NgY3+SJyJk+StrYvS4hZOXwg0iyiMrB1iwWmlaZkb8YR3AkBTauBwPeBfynLizUxbxhCLtmCX"
				+ "OYclWLEBZtqWtFFL3ngYoN3cCGqAU9yvxRKcrYzSQa8p1FddXCkNtGBQHMPFAkEAgCyNSn6QBBwY"
				+ "DipdZX+tGthzzTPnyfYJVLwyO0/pfTOA0wdLyhsC4nAd8qaxNkSJPTPU+a2R5Q+8yxLw7rRtQwJA"
				+ "eSj6/m5EOh+dCad0hyOZnzIO4Xaf8O9vujm6DeZ/smt3eLZis3OrlOwiReYb48R5N4OtUcJXMfgh" + "BFTf7QBMSw==";
        //生成签名
		RSAPrivateKey privateKey = getprivateKeyBykey(priKey);
		String sign = generalSign("1qaz2wsx".getBytes("UTF-8"), privateKey);


		RSAPublicKey publicKey = getPublicKeyBykey(
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAt+pY4cVasdQ4KVAvGUYaUnDrPV9xREs0o3Ly1b9kl6cLjBBySI+0aQcpO6yeBVR315ednKLTHQ1VouwUUEM/cYSMciRvx+M3nqA0B44Ozx2BQ01XbDfw0Rw79bYqTgZ8tCK6xlluu+0onemf7DHlKSjy//PnloeycDtWq1J94QIDAQAB");
		//验签
		Boolean is = signVerify("1qaz2wsx", sign, publicKey);
		//公钥加密
		String s = publicKeyEncrypt("18888888888", publicKey);
		//私钥解密
		String s1 = privateKeyDecrypt(s, privateKey);

		System.out.println(s1);

	}
}
