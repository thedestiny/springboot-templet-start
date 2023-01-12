package com.destiny.squirrel.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @Description
 * @Author destiny
 * @Date 2021-10-19 8:10 PM
 */
@Slf4j
public class AESUtils {

    // 定义字符集
    private static final String ENCODING = "UTF-8";

    /**
     * 根据提供的密钥生成AES专用密钥
     *
     * @param password 可以是中文、英文、16进制字符串
     * @return AES密钥
     * @throws Exception
     * @explain
     */
    public static byte[] generateKey(String password) throws Exception {
        byte[] keyByteArray = null;
        // 创建AES的Key生产者
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // 利用用户密码作为随机数初始化
        // 128位的key生产者
        // 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        /*
         * 只适用windows
         * kgen.init(128, new SecureRandom(password.getBytes(ENCODING)));
         */

        // 指定强随机数的生成方式
        // 兼容linux
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes(ENCODING));
        kgen.init(128, random);// 只能是128位

        // 根据用户密码，生成一个密钥
        SecretKey secretKey = kgen.generateKey();
        // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
        keyByteArray = secretKey.getEncoded();
        return keyByteArray;
    }

    public static String encrypt(String content, String password) {
        String cipherHexString = "";// 返回字符串
        try {
            // 转换为AES专用密钥
            byte[] keyBytes = generateKey(password);

            SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            // 将待加密字符串转二进制
            byte[] clearTextBytes = content.getBytes(ENCODING);
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            // 加密结果
            byte[] cipherTextBytes = cipher.doFinal(clearTextBytes);
            // byte[]-->hexString
            cipherHexString = toHexString(cipherTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AES加密失败：" + e.getMessage());
        }
        log.info("AES加密结果：" + cipherHexString);
        return cipherHexString;
    }

    public static String toHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String decrypt(String hexString, String password) {
        String clearText = "";
        try {
            // 转换为AES专用密钥
            byte[] keyBytes = generateKey(password);

            SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, sks);
            // hexString-->byte[]
            // 将16进制密文转换成二进制
            byte[] cipherTextBytes = hexStringToByte(hexString);
            // 解密结果
            byte[] clearTextBytes = cipher.doFinal(cipherTextBytes);
            // byte[]-->String
            clearText = new String(clearTextBytes, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AES解密失败：" + e.getMessage());
        }
        log.info("AES解密结果：" + clearText);
        return clearText;
    }

    public static byte[] hexStringToByte(String hexString) {
        if (StrUtil.isEmpty(hexString))
            return null;
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }


    public static void main(String[] args) {

        String json = "测试数据";
        String password = "123456";
        // 加密
        String encrypt = encrypt(json, password);
        System.out.println(encrypt);
        // 解密
        String decrypt = decrypt(encrypt, password);
        System.out.println(decrypt);


    }
}
