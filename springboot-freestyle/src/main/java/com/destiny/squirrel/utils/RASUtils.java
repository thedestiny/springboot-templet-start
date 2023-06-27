package com.destiny.squirrel.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description rsa sign
 * @Author destiny
 * @Date 2021-10-28 5:34 PM
 */
public class RASUtils {

    // 己方私钥
    private static String PRIVATE_KEY = "MIIJQwIBADANBgkqhkiG";
    // 合作方公钥
    private static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0B";


    public static void main(String[] args) {

        // https://blog.csdn.net/zyw61483/article/details/124842836
        JSONObject json = new JSONObject();
        json.put("msg", "msg");
        json.put("data", "123456");
        json.put("code", "0000");

        JSONObject node = new JSONObject(false);
        node.put("name", "小明");
        node.put("age", "12");
        node.put("address", "浙江省");
        node.put("weight", "23.5");
        json.put("node", node);
        // , SerializerFeature.MapSortField
        String s1 = JSON.toJSONString(json, SerializerFeature.MapSortField);
        System.out.println(s1);

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


        Sign sign = new Sign(SignAlgorithm.MD5withRSA.getValue(), privateKeyBase64, publicKeyBase64);
        String s = sign.signHex("12345678");
        System.out.println("sign is " + s);


    }


    /**
     * 合作方返回结果时，会用我方公钥加密
     * 我们收到返回结果时，用我方私钥解密
     */
    private static String decryptByPriKey(String resp) {
        RSA rsa = new RSA(PRIVATE_KEY, null);
        Map<String, Object> map1 = GsonUtil.fromJson2Map(resp);
        return rsa.decryptStr(map1.get("bizContent").toString(), KeyType.PrivateKey);
    }

    /**
     * 合作方返回结果时，会用合作方私钥加签
     * 我们收到返回结果时，用合作方公钥验签
     */
    private static boolean checkSignByPubKey(Map<String, Object> respMap) {
        String signValue = (String) respMap.get("sign");
        respMap.remove("sign");
        String s = GsonUtil.toJsonString(respMap);
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, null, PUBLIC_KEY);
        return sign.verify(s.getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(signValue.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 合作方公钥加密
     * 合作方收到请求后，用合作方私钥解密
     */
    private static Object encryptedByPubKey(Map<String, Object> bizMap) {
        TreeMap<String, Object> sort = MapUtil.sort(bizMap);
        String sortJson = GsonUtil.toJsonString(sort);
        RSA pubRsa = new RSA(null, PUBLIC_KEY);
        byte[] encryptedData = pubRsa.encrypt(sortJson.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 己方私钥加签
     * 合作方收到请求后，用我方公钥验签
     */
    public static String signByPriKey(Map<String, Object> params) {
        TreeMap<String, Object> sort = MapUtil.sort(params);
        String paramsJson = GsonUtil.toJsonString(sort);
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, PRIVATE_KEY, null);
        return Base64.encodeBase64String(sign.sign(paramsJson.getBytes(StandardCharsets.UTF_8)));
    }

    static class GsonUtil {
        static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        public static String toJsonString(Object obj) {
            return gson.toJson(obj);
        }

        public static Map<String, Object> fromJson2Map(String obj) {
            TypeToken<Map<String, Object>> mapTypeToken = new TypeToken<Map<String, Object>>() {
            };
            return fromJson2Type(obj, mapTypeToken);
        }

        public static <T> T fromJson2Type(String obj, TypeToken typeToken) {
            return gson.fromJson(obj, typeToken.getType());
        }
    }


}
