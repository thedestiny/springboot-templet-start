package com.destiny.origin.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HmacTest {

    public static void main(String[] args) throws Exception {
        String keySecret = "123456";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "1300319277300421122");
        String param = JSONObject.toJSONString(map);
        String sign = hmacSHA1Encrypt(param, keySecret);

        String KeyId = "1300319277300421122";

        String authorization = "API " + KeyId + ":" + sign;
        System.out.println(authorization);
        String digest = getDigest(authorization);
        System.out.println(" md5 " + digest);
        System.out.println(" sign " + sign);

        String gmtTime = getGMTTime();

        HttpRequest post = HttpUtil.createPost("https://api.ginlong.com:13333/v1/api/userStationList");
        post.header("Date", gmtTime);
        post.header("Content_MD5", gmtTime);
        post.header("Content_Type", "application/json");
        post.header("Authorization", authorization);

        HttpResponse execute = post.execute();
        String body1 = execute.body();
        System.out.println(body1);


    }

    public static String hmacSHA1Encrypt(String encryptText, String KeySecret) throws Exception {
        System.out.println(encryptText);
        byte[] data = KeySecret.getBytes("UTF-8");
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes("UTF-8");
        //完成 Mac 操作
        byte[] result = mac.doFinal(text);
        String s = Base64.encodeBase64String(result);
        System.out.println(" sign test ::  " + s);

        return s ;
    }

    public static String getGMTTime() {

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        System.out.println(str);
        return str;
    }

    public static String getDigest(String test) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(test.getBytes());
            byte[] b = md.digest();
            result = java.util.Base64.getEncoder().encodeToString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }


}
