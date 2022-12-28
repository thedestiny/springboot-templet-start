package com.destiny.squirrel.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-10-28 5:34 PM
 */

@Slf4j
public class RASUtils2 {

    // DH 密钥交换算法
    // RSA 密钥加密 2048 密钥的长度 2048 表示比特位 即 256 字节
    // 1 : p * q = n
    // E = (p -1 )* (q - 1)
    // e : 1 < e < E , e E 互为质数, 选出公钥
    // 计算私钥 e*d%E=1
    // 公钥 (n, e) 私钥 (n, d)

    // https://blog.csdn.net/qq_34486648/article/details/123984664
    // https://blog.csdn.net/qq_44750892/article/details/120075922



    public static void main(String[] args) throws IOException {

        ClassPathResource priKey = new ClassPathResource("/file/pri_key.txt");
        ClassPathResource pubKey = new ClassPathResource("/file/pub_key.txt");

        String priStr = IOUtils.toString(priKey.getStream(), StandardCharsets.UTF_8);
        String pubStr = IOUtils.toString(pubKey.getStream(), StandardCharsets.UTF_8);


//        byte[] cfs = HexUtil.decodeHex("ff1790033dd85ed3f78df634732c09cf");
//        System.out.println(cfs);


        RSA rsa1 = new RSA();
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(),
                priStr,
                pubStr);

        //获得私钥
        PrivateKey privateKey = rsa.getPrivateKey();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        //获得公钥
        PublicKey publicKey = rsa.getPublicKey();
        String publicKeyBase64 = rsa.getPublicKeyBase64();


        Sign sign = new Sign(SignAlgorithm.MD5withRSA, privateKey,publicKey);


        // 签名和验签 
        String data = "我是一段测试aaaa";
        byte[] sign1 = sign.sign(data);
        boolean verify = sign.verify(data.getBytes(StandardCharsets.UTF_8), sign1);
        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(data, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String s = rsa.encryptBase64(data, KeyType.PublicKey);
        log.info("pub encrypt byte {}", Base64.encode(encrypt));
        log.info("pub encrypt {}", s);


        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        char[] chars = HexUtil.encodeHex(encrypt);
        // 512 * 16
        System.out.println(chars);
        log.info(" data 1 {} ", HexUtil.encodeHexStr(encrypt));
        Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

        log.info(" data 2 {} ",StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

    }


}
