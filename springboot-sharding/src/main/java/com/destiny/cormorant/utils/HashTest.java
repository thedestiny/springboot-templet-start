package com.destiny.cormorant.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.AES;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @Description
 * @Date 2022-08-14 9:40 PM
 */
@Data
@Slf4j
public class HashTest {

    private Integer age;
    private String name;

    public static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
		/*这里面为啥用个31来计算，而且很多人都是这么写的，
		这是因为31是个神奇的数字，任何数n*31都可以被jvm优化为(n<<5)-n，
		移位和减法的操作效率比乘法的操作效率高很多！*/
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        //当这个域的值为null的时候（即 string == null 时），那么hashCode值为0
        result = prime * result + age;
        return result;
    }

    public static void main(String[] args) {

        System.out.println(1 << 16);

        HashTest obj = new HashTest();
        obj.setAge(12);
        obj.setName("小李");
        log.info("hash {}", obj.hashCode());
        // identityHashCode 对象内存地址返回一个hash值, 不管对象是否重载了hashCode方法，
        // identityHashCode方法都会返回该类默认 hashCode 方法会返回的值
        int i = System.identityHashCode(obj);
        log.info("identityHashCode {}", i);
        log.info("toHexString {}", Integer.toHexString(i));
        log.info("hash {}", hash(obj));

        AES aes = new AES(aes_key.getBytes());
        String encrypt = aes.encryptBase64("123456");
        System.out.println(encrypt);
        System.out.println(encrypt("123456"));


    }

    /**
     * 默认的AES加密方式：AES/ECB/PKCS5Padding
    AES("AES"),
    ARCFOUR("ARCFOUR"),
    Blowfish("Blowfish"),
    默认的DES加密方式：DES/ECB/PKCS5Padding
    DES("DES"),
    3DES算法，默认实现为：DESede/CBC/PKCS5Padding
    DESede("DESede"),
    RC2("RC2"),
     */

    private static final String aes_key = "1111222233334444";

    public static String encrypt(String cleartext) {
        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：1111222233334444
        try {
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            SecretKeySpec key = new SecretKeySpec(aes_key.getBytes(), "AES");
            //实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, key); //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式

            byte[] encryptedData = cipher.doFinal(cleartext.getBytes(CharsetUtil.CHARSET_UTF_8));
            byte[] bytes = new BASE64Encoder().encode(encryptedData).getBytes(CharsetUtil.CHARSET_UTF_8);

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
