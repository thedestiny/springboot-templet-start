package com.destiny.origin.utils;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 表达式计算
 * @Author liangwenchao
 * @Date 2022-04-22 10:12 AM
 */

@Slf4j
public class ExpressionUtils {


    public static void main(String[] args) throws Exception {


        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a",1);
        context.put("b",2);
        context.put("c",3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);

        log.info(" result is {}",r);

        ExpressionEvaluator evaluator = new ExpressionEvaluator();

        Map<String, String> params = new HashMap<>();
        params.put("name", "44");
        boolean b = evaluator.evaluateBoolean("name != null and name != '小明'", params);
        boolean g = evaluator.evaluateBoolean("( 3 + 3) == 3 ", params);
        log.info("b is {} g  {}",b,g);


        String dd = traditional("正义的战争");
        System.out.println(dd);
        String simple = simple("詞彙翻譯邏輯");
        System.out.println(simple);



    }

    public static String simple(String pinYinSt) {
        String tempStr = null;
        try {

            tempStr = ChineseHelper.convertToSimplifiedChinese(pinYinSt);
        } catch (Exception e) {
            tempStr = pinYinSt;
            e.printStackTrace();
        }

        return tempStr;
    }

    public static String traditional(String pinYinStr) {
        String tempStr = null;
        try {
            tempStr = ChineseHelper.convertToTraditionalChinese(pinYinStr);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        return tempStr;
    }


}
