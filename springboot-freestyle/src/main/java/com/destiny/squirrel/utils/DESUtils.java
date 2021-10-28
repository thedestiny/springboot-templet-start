package com.destiny.squirrel.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-10-28 5:34 PM
 */
@Slf4j
public class DESUtils {


    public static void main(String[] args) {

        String content = "test中文";

        // KeyUtil.generateKey();
        // KeyUtil.generatePrivateKey();

        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
        System.out.println(secretKey.getAlgorithm());

        //随机生成密钥
        byte[] key = secretKey.getEncoded();

        String priKey = HexUtils.byteArrToHex(key);
        System.out.println("priKey " + priKey);

        byte[] bytes = HexUtils.hexToByteArr(priKey);
        // 对比数组
        System.out.println(ArrayUtil.equals(key, bytes));

        byte[] cfs = HexUtil.decodeHex("ff1790033dd85ed3f78df634732c09cf");
        System.out.println(cfs);

        //构建
        AES aes = SecureUtil.aes(key);
        log.info(" aes key {}", aes.getSecretKey().getFormat());

        //加密
        byte[] encrypt = aes.encrypt(content);
        //解密
        byte[] decrypt = aes.decrypt(encrypt);

        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        log.info("加密后内容 encryptHex {}", encryptHex);
        //解密为原字符串
        String decryptStr = aes.decryptStr(encryptHex);
        log.info("解密后内容 decryptStr {}", decryptStr);


    }

}
