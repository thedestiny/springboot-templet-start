package com.destiny.squirrel.config;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Date 2023-06-27 2:10 PM
 */
@Slf4j
public class DensUtils {

    public static String toEncryptView(String strVal, String spanChar, int beginIdx, int endIdx) {
        // 不为空
        if (StrUtil.isNotEmpty(strVal)) {
            // 修正开始下标位移问题
            if (beginIdx < 0) {
                beginIdx = 0;
            }
            // 修正结束下标位移问题
            if (endIdx > strVal.length()) {
                endIdx = strVal.length() - 1;
            }
            // 处理结束下标小于开始下标的问题
            if (beginIdx > endIdx) {
                endIdx = beginIdx;
            }
            StringBuilder builder = new StringBuilder();
            // 如果结束下标是0
            if (endIdx == 0) {
                builder.append(spanChar);
            } else {
                // 循环处理加密字符
                char[] chars = strVal.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (i >= beginIdx && i <= endIdx) {
                        builder.append(spanChar);
                    } else {
                        builder.append(chars[i]);
                    }
                }
            }
            return builder.toString();
        }
        return strVal;
    }


    public static void main(String[] args) {


        String phone = "13849869923";
        String s = toEncryptView(phone, "*", 3, 6);
        log.info("character {} and  encrypt {}", phone, s);
        String s1 = DesensitizedUtil.mobilePhone(phone);
        log.info("phone is {}", s1);

        String s2 = DesensitizedUtil.idCardNum("51343620000320711X", 5, 2);
        log.info("id card {}", s2);
        String email = DesensitizedUtil.email("1792561025@qq.com");
        log.info("email  {}", email);

        String bankCard = DesensitizedUtil.bankCard("9559980868435875810");
        log.info("bank card {} ", bankCard);

        String carLicense = DesensitizedUtil.carLicense("陕A12345D");
        log.info("car license {} ", carLicense);

    }
}
