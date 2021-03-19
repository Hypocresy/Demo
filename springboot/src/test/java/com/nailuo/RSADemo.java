package com.nailuo;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/1/22 17:08
 * @since 0.0.1
 * 世界上并没有完美的程序，但我们并不因此而沮丧，因为写程序本来就是一个不断追求完美的过程。
 */
public class RSADemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        byte[] publicBytes = pair.getPublic().getEncoded();
        byte[] privateBytes = pair.getPrivate().getEncoded();

        System.out.println("public key: " + base64Encode(publicBytes));
        System.out.println("private key: " + base64Encode(privateBytes));
    }

    static String base64Encode(byte[] bytes) {

        return Base64.getEncoder().encodeToString(bytes);
    }
}
