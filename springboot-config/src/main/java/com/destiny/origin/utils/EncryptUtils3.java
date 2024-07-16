package com.destiny.origin.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author destiny
 * @Date 2022-02-25 3:41 PM
 */
@Slf4j
public class EncryptUtils3 {

    public static void main(String[] args) throws Exception {

        String priKey = FileUtil.readString("./ras-privatekey", "UTF-8");

        System.out.println(priKey);

        // https://blog.csdn.net/qq_36761831/article/details/91448215

        RSA rsa1 = new RSA(priKey, null);

        String encrypt = "";

        // 0bd3c31a-8a38-45c1 PROD_longem-caccount-service
        String decrypt = rsa1.decryptStr(encrypt, KeyType.PrivateKey);

        System.out.println(decrypt);

    }


}
