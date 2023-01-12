package com.destiny.origin.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.function.*;

/**
 * @Description
 * @Author destiny
 * @Date 2022-04-27 10:57 AM
 */
@Slf4j
public class Java8Function {

    public static void main(String[] args) {

        // predicate 用于判断
        Predicate<String> predicate = node -> node.equals("123");
        boolean test = predicate.test("456");

        // function 一个入参 一个出参
        Function<String, Integer> function = (node) -> Integer.valueOf(node);
        Integer apply = function.apply("123");

        // 和 function 类似，入参和出参类型一致
        UnaryOperator<String> operator1 = (node) -> node + " result";
        log.info("operator1 result {}", operator1.apply("44"));

        // BiFunction 两个入参，一个返回值
        BiFunction<String, String, Integer> function1 = (s1, s2) -> Integer.valueOf(s1) + Integer.valueOf(s2);
        Integer apply1 = function1.apply("2", "5");

        // 和 BiFunction 类似，只是入参和返回值类型一致
        BinaryOperator<Integer> operator = (j, k) -> j + k;
        Integer apply2 = operator.apply(2, 4);

        // consumer 消费一个参数，无返回值 类似的还有 BiConsumer
        Consumer<String> consumer = (node) -> System.out.println("params " + node);
        consumer.accept("3");

        // supplier 提供一个参数 ，没有入参
        Supplier<Integer> supplier = () -> 3;
        Integer apply4 = supplier.get();
        log.info("supplier {} ", apply4);

        log.info("\npredicate result {}\nFunction result {}\nBiFunction result {} \nBinaryOperator result {}", test, apply, apply1, apply2);

    }

}
