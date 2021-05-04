package com.liuyongyu;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;

public class AES {

    public static final String ALGORITHM_NAME ="AES";

    public static final String CHARSET = "UTF-8";

    private static final Integer keySize = 256;  //must be equal to 128, 192 or 256

    public static String generator() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();
            String encode = Base64.encode(secretKey.getEncoded());
            return encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Key getKey(String key) throws Base64DecodingException {
        Key secretKeySpec = new SecretKeySpec(Base64.decode(key), ALGORITHM_NAME);
        return secretKeySpec;
    }

    public static String encrypt(String data, String key){
        try {
            Key secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = doFinal(cipher, Cipher.ENCRYPT_MODE, data.getBytes());
            return Base64.encode(bytes);
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data, String key){
        try {
            Key secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME + "/ECB/PKCS5Padding");
            cipher.init(DECRYPT_MODE, secretKey);
            byte[] bytes = doFinal(cipher, Cipher.DECRYPT_MODE, Base64.decode(data));
            return new String(bytes, CHARSET);
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] doFinal(Cipher cipher, int cipherModel, byte[] data) throws Exception {
        //直接使用RSA中写好的
        return RSA.doFinal(cipher, cipherModel, data);
    }
}
