package src.main.java.com.hy.demo;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;

/**
 * @author hy
 * @blame Development Group
 * @date 2020/4/24 14:30
 * @since 0.0.1
 */
public class CipherDemo {

    public String slatKey ="sadas1515d48slo6";
    public String vectorKey ="sadas1515d48slo4";

    public String encrypt(String content) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKey aes = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE,aes,ivParameterSpec);
        byte[] doFinal = cipher.doFinal(Base64.decode(content));
        System.out.println(doFinal);
        return Base64.encode(doFinal);
    }
    @Test
    public void test(){
        try {
           String encrypt = encrypt("hy19970625");
           System.out.println(encrypt);
            String decrypt = decrypt(encrypt, slatKey, vectorKey);
            System.out.println(decrypt);
        }catch (Exception e){
          e.printStackTrace();
        }
    }

    /**
     * content: 加密内容
     * slatKey: 加密的盐，16位字符串
     * vectorKey: 加密的向量，16位字符串
     */
    public String encrypt(String content, String slatKey, String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return Base64.encode(encrypted);
    }

    /**
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐，16位字符串
     * vectorKey: 加密时使用的向量，16位字符串
     */
    public String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
        new HashMap<>();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] content = Base64.decode(base64Content);
        byte[] encrypted = cipher.doFinal(content);
        return new String(encrypted);
    }

    public  void sadad(){

    }
}
