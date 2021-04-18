package com.liuyongyu;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    public static final String ALGORITHM_NAME ="RSA";

    public static final String CHAESET = "UTF-8";

    private static final Integer keySize = 1024;


    public RSA() {
    }

    public static class KeyPairBase64{
        private String publicKey = "";
        private String privateKey = "";

        public KeyPairBase64(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public KeyPairBase64() {
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }
    }

    public static KeyPairBase64 generator(){
        KeyPairGenerator keyPairGenerator = null;
        KeyPairBase64 keyPairBase64 = null;
        try{
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();
            /**
             * 使用base64进行编码，将公私钥转换为字符串
             */
            String pbcKey = Base64.encode(publicKey.getEncoded());
            String prvKey = Base64.encode(privateKey.getEncoded());

            keyPairBase64 = new KeyPairBase64(pbcKey, prvKey);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return keyPairBase64;
    }

    private static PublicKey getPublicKey(String pubKey) throws NoSuchAlgorithmException, InvalidKeySpecException, Base64DecodingException {
        KeyFactory key = KeyFactory.getInstance(ALGORITHM_NAME);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(pubKey));
        PublicKey publicKey = key.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }

    private static PrivateKey getPrivateKey(String prvKey) throws NoSuchAlgorithmException, InvalidKeySpecException, Base64DecodingException {
        KeyFactory key = KeyFactory.getInstance(ALGORITHM_NAME);
        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(prvKey));
        PrivateKey privateKey = key.generatePrivate(x509EncodedKeySpec);
        return privateKey;
    }

    /**
     * 公钥加密
     * @param data
     * @param pbcKey
     * @return
     */
    public static String publicKeyEncrypt(String data, String pbcKey){
        try {

            PublicKey publicKey = getPublicKey(pbcKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = doFinal(cipher, Cipher.ENCRYPT_MODE, data.getBytes());
            return Base64.encode(bytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     * @param data
     * @param prvKey
     * @return
     */
    public static String privateKeyEncrypt(String data, String prvKey){
        try {

            PrivateKey privateKey = getPrivateKey(prvKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = doFinal(cipher,Cipher.ENCRYPT_MODE, data.getBytes());
            return Base64.encode(bytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     * @param data
     * @param pbcKey
     * @return
     */
    public static String publicKeyDecrypt(String data, String pbcKey){
        try {

            PublicKey publicKey = getPublicKey(pbcKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = doFinal(cipher, Cipher.DECRYPT_MODE, Base64.decode(data));
            return new String(bytes, CHAESET);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     * @param data
     * @param prvKey
     * @return
     */
    public static String privateKeyDecrypt(String data, String prvKey){
        try {

            PrivateKey privateKey = getPrivateKey(prvKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = doFinal(cipher, Cipher.DECRYPT_MODE, Base64.decode(data));
            return new String(bytes, CHAESET);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  分块加载
     * @param cipher
     * @param data
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    private static byte[] doFinal(Cipher cipher, int cipherModel, byte[] data) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffe;
        int offset = 0, len = data.length;
        final int maxLen;
        if(cipherModel == Cipher.ENCRYPT_MODE){
            maxLen = keySize / 8 - 11;
        }else if(cipherModel == Cipher.DECRYPT_MODE){
            maxLen = keySize / 8;
        }else{
            throw new Exception("cipherMode参数错误");
        }
        byte[] res = null;
        try{
            while(len > offset){
                if(len > offset + maxLen){
                    buffe = cipher.doFinal(data, offset, maxLen);
                    offset += maxLen;
                }else{
                    buffe = cipher.doFinal(data, offset, len - offset);
                    offset += len;
                }
                out.write(buffe);
            }
            res = out.toByteArray();
        }finally {
            out.close();
        }
        return res;
    }
}
