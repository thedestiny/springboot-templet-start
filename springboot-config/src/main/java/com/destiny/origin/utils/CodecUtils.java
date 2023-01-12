package com.destiny.origin.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.PemUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * @Description
 * @Author destiny
 * @Date 2022-04-24 11:11 AM
 */
@Slf4j
public class CodecUtils {


    /**
     * 加解密并且验签
     */
    public static void encryptDecryptSign() {

        // 读取配置的公钥和私钥
        PrivateKey privateKey = PemUtil.readPemPrivateKey(new ClassPathResource("config/rsa_private.pem").getStream());
        PublicKey publicKey = PemUtil.readPemPublicKey(new ClassPathResource("config/rsa_public.pem").getStream());
        // 公钥加密 私钥解密
        // 私钥加密 公钥验签
        String algorithm = privateKey.getAlgorithm();
        String format = privateKey.getFormat();
        log.info("algorithm {} format {} ", algorithm, format);

        RSA rsa = new RSA(privateKey, publicKey);

        // 需要加密的内容
        String content = "我是一段测试";
        // 公钥加密生成秘文
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(content, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String encode = Base64.encode(encrypt);
        log.info("加密后的秘文 {}", encode);

        // 私钥解密生成明文
        byte[] decrypt = rsa.decrypt(Base64.decode(encode), KeyType.PrivateKey);
        String decryptStr = StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
        log.info("解密后的明文 {}", decryptStr);


        // 生成签名
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, rsa.getPrivateKeyBase64(), null);
        byte[] signed = sign.sign(content.getBytes());
        String base64 = Base64.encode(signed);
        log.info("生成的签名为 {}", base64);

        // 选择验证方式 以及 设置公钥 签名作用为防篡改
        // 验证签名
        byte[] decode = Base64.decode(base64);
        Sign sign1 = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, rsa.getPublicKeyBase64());
        boolean verify = sign1.verify(content.getBytes(), decode);
        log.info("签名验证结果 {}", verify);

    }


    public static void main(String[] args) {


        String bcrypt = DigestUtil.md5Hex("333");
        System.out.println(bcrypt);

        // function
        BiFunction<Integer, Integer, Integer> func = (x1, x2) -> x1 + x2;
        Integer result = func.apply(2, 3);

        // BinaryOperator
        BinaryOperator<Integer> func2 = (x1, x2) -> x1 + x2;

        Integer apply = func2.apply(3, 5);
        System.out.println("result " + result + "  apply " + apply);


        token();


    }

    public static void token() {


        // 两次生成的密码不一样，需要使用 match 进行校验 bcrypt
        String bcrypt1 = DigestUtil.bcrypt("123456");
        String bcrypt2 = DigestUtil.bcrypt("123456");
        boolean b = DigestUtil.bcryptCheck("123456",bcrypt1 );
        log.info("bcrypt1 {} bcrypt2 {} flag {}", bcrypt1, bcrypt2, b);


    }


}
