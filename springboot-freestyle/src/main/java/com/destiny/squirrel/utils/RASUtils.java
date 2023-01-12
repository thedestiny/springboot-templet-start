package com.destiny.squirrel.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Assert;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Description
 * @Author destiny
 * @Date 2021-10-28 5:34 PM
 */
public class RASUtils {


    public static void main(String[] args) {

        byte[] cfs = HexUtil.decodeHex("ff1790033dd85ed3f78df634732c09cf");
        System.out.println(cfs);

        RSA rsa = new RSA();

        //获得私钥
        PrivateKey privateKey = rsa.getPrivateKey();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        //获得公钥
        PublicKey publicKey = rsa.getPublicKey();
        String publicKeyBase64 = rsa.getPublicKeyBase64();

        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));


    }


}
