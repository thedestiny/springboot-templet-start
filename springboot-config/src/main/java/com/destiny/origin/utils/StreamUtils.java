package com.destiny.origin.utils;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-02-28 9:55 AM
 */
public class StreamUtils {

    public static void main(String[] args) {

        // https://mp.weixin.qq.com/s/V1CHb5UDrD1Zz0EswbUwBg

        List<String> list = Arrays.asList("a", "b", "c");
        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallel = list.parallelStream();

        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> stream2 = Stream.iterate(4, (x) -> x + 3).limit(9);
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);

        stream2.forEach(System.out::println); // 0 2 4 6 8 10

        List<Integer> list1 = Lists.newArrayList(6, 8, 10);
        Optional<Integer> findFirst = list1.stream().parallel().filter(x -> x > 6).findFirst();
        System.out.println(findFirst.get());

        List<String> list2 = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list2.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());

        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> intListNew = intList.stream().map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + strList);
        System.out.println("每个元素+3：" + intListNew);

        reduce002();


    }

    public static void reduce002() {
        List<BigDecimal> result = new ArrayList<>();
        result.add(BigDecimal.valueOf(3));
        result.add(BigDecimal.valueOf(1));
        result.add(BigDecimal.valueOf(5));
        result.add(BigDecimal.valueOf(7));

        BigDecimal reduce = result.stream().reduce(BigDecimal::add).get();
        System.out.println(reduce);


    }


    /**
     * partitioningBy
     * groupingBy
     */
    public void reduce001() {

        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);

        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);

        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);

        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);

        // String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
    }

}
