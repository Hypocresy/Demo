package com.nailuo.util;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * @author 64421
 */
public class SM3Util {
	
	/**
	* 
	* @param message 需要进行签名的内容
	* @param secret hmac秘钥
	* @return
	*/
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
