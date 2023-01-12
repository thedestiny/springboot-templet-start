package com.destiny.origin.utils.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Description
 * @Author destiny
 * @Date 2022-04-26 4:30 PM
 */
@Slf4j
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/PKCS5Padding";

    private AESUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            cipher.init(1, getSecretKey(password));
            byte[] iv = cipher.getIV();

            assert iv.length == 12;

            byte[] encryptData = cipher.doFinal(content.getBytes());

            assert encryptData.length == content.getBytes().length + 16;

            byte[] message = new byte[12 + content.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return Base64.encodeBase64String(message);
        } catch (Exception var6) {
            log.info("AESUtil#encrypt拋出异常,password:{},content:{}", new Object[]{password, content, var6});
            return null;
        }
    }

    public static String decrypt(String base64Content, String password) {
        try {
            byte[] content = Base64.decodeBase64(base64Content);
            if (content.length < 28) {
                throw new IllegalArgumentException();
            } else {
                GCMParameterSpec params = new GCMParameterSpec(128, content, 0, 12);
                Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
                cipher.init(2, getSecretKey(password), params);
                byte[] decryptData = cipher.doFinal(content, 12, content.length - 12);
                return new String(decryptData);
            }
        } catch (Exception var6) {
            log.info("AESUtil#decrypt拋出异常,password:{},content:{}", new Object[]{password, base64Content, var6});
            return null;
        }
    }

    private static SecretKeySpec getSecretKey(String encryptPass) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encryptPass.getBytes());
        kg.init(128, random);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }


}
