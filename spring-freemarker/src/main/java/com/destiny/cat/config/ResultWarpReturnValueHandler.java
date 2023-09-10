package com.destiny.cat.config;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.destiny.cat.anno.JsonEncrypt;
import com.destiny.cat.util.EncryptViewUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * 字段加密返回
 * @Description
 * @Date 2023-04-21 10:44 AM
 */

@Slf4j
public class ResultWarpReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;
    public ResultWarpReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer container, NativeWebRequest request) throws Exception {
        delegate.handleReturnValue(convertReturnValue(returnValue), returnType, container, request);
    }
    // 转换返回值
    private Object convertReturnValue(Object source) {
        if (null != source) {
            jsonEncrypt(source);
        }
        return source;
    }
    // 加密内容
    private void jsonEncrypt(Object source) {
        // 判断返回值的内容
        if (source instanceof List) {
            Iterable iterable = (Iterable) source;
            for (Object object : iterable) {
                doJsonEncrypt(object);
            }
        } else if (source instanceof Result) {
            Result result = (Result) source;
            if (null != result.getData()) {
                doJsonEncrypt(result.getData());
            }
            if (null != result.getData() && (result.getData() instanceof Page)) {
                Iterable iterable = (Iterable) ((Page<?>) result.getData()).getRecords();
                for (Object object : iterable) {
                    doJsonEncrypt(object);
                }
            }
            if (null != result.getData() && (result.getData() instanceof List)) {
                Iterable iterable = (Iterable) result.getData();
                for (Object object : iterable) {
                    doJsonEncrypt(object);
                }
            }
        } else {
            doJsonEncrypt(source);
        }
    }
    // 加密内容
    private void doJsonEncrypt(Object object) {
        // 获取对象的所有字段
        Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                JsonEncrypt jsonEncrypt = field.getAnnotation(JsonEncrypt.class);
                if (null != jsonEncrypt) {
                    doJsonEncrypt(field, jsonEncrypt, object);
                }
            }
        }
    }

    private static String key = "ab2252b555f7420c";
    // 加密返回值
    private void doJsonEncrypt(Field field, JsonEncrypt jsonEncrypt, Object object) {
        try {
            field.setAccessible(true);
            Object val = field.get(object);
            if (null != val) {
                // 加密值
                String strVal = String.valueOf(val);
                // 加密参数
                String spanChar = jsonEncrypt.value();
                int type = jsonEncrypt.type();
                int beginIdx = jsonEncrypt.beginIdx(), endIdx = jsonEncrypt.endIdx();
                // 加密或者脱敏
                if(type == 1){
                    DES des = SecureUtil.des(key.getBytes());
                    String s1 = des.encryptBase64(strVal);
                    field.set(object, s1);
                } else {
                    // 设置加密后的值
                    field.set(object, EncryptViewUtils.toEncryptView(strVal, spanChar, beginIdx, endIdx));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        String data = "这是一个小例子haha";
        String pubKey = "xxxxx";
        String priKey = "xxxxx";

        // 初始化RSA工具并设置私钥
//        RSA priRsa = new RSA(priKey, null);
//        // 初始化RSA工具并设置公钥
//        RSA pubRsa = new RSA(null, pubKey);
//        // 初始化自定义密钥对RSA工具，但是一般不这么用，因为公私钥需要分离
//        RSA Rsa = new RSA(priKey, pubKey);


        String key = "ab2252b555f7420c";
        System.out.println(key.length());
        String iv2 = "ab2252b555f7420c";
        System.out.println(UUID.randomUUID().toString());

        AES aes2 = new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv2.getBytes());

        String tesxts = aes2.encryptBase64("tesxt");


        String s = aes2.decryptStr(tesxts);
        System.out.println(s);

        System.out.println("dddddddddddddd");
        DES des = SecureUtil.des(key.getBytes());
        String s1 = des.encryptBase64("33333333333333333333");

        String s2 = des.decryptStr(s1);
        System.out.println(s2);

    }
}
