package com.destiny.origin.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Description
 * @Author destiny
 * @Date 2022-02-25 3:41 PM
 */
@Slf4j
public class EncryptUtils {

    public static void main(String[] args) {



//
//        //获得私钥
//        PrivateKey privateKey = rsa.getPrivateKey();
//        //获得公钥
//        PublicKey publicKey = rsa.getPublicKey();
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();

        log.info("private key {}",privateKeyBase64);
        log.info("public key {}",publicKeyBase64);

        String priKey = ResourceUtil.readStr("config/pri_key.txt", Charset.forName("UTF-8"));
        String pubKey = ResourceUtil.readStr("config/pub_key.txt", Charset.forName("UTF-8"));

        RSA rsa1 = new RSA(priKey, pubKey);

        //公钥加密，私钥解密
        byte[] encrypt = rsa1.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String encrypt3 = rsa1.encryptBase64(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.out.println("加密秘文 " + StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8));
        System.out.println("加密秘文3 " + encrypt3);
        byte[] decrypt = rsa1.decrypt(encrypt, KeyType.PrivateKey);
        String decrypt3 = rsa1.decryptStr(encrypt3, KeyType.PrivateKey);
        String str = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密明文 " + str);
        System.out.println("解密明文3 " + decrypt3);


//私钥加密，公钥解密
        byte[] encrypt2 = rsa1.encrypt(StrUtil.bytes("我是一段测试bbb", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        System.out.println("加密秘文1" + StrUtil.str(encrypt2, CharsetUtil.CHARSET_UTF_8));
        byte[] decrypt2 = rsa1.decrypt(encrypt2, KeyType.PublicKey);
        String str1 = StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密明文1" + str1);


    }


}
