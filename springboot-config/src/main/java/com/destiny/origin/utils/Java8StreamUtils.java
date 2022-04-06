package com.destiny.origin.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class Java8StreamUtils {

    public static void main(String[] args) {


        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        Integer[] nums = new Integer[10];

        Stream<Integer> stream1 = Arrays.stream(nums);

        // of 创建 stream
        Stream<String> strs = Stream.of("a","b","c","d","d","e");
        Stream<String> newStream = strs.filter(s -> s.compareTo("b") > 0)
                .distinct()
                .skip(2)
                .limit(2);
        newStream.forEach(System.out::println);

        // lambda 创建等差数列，获取前 3 个
        Stream<Integer> stream2 = Stream.iterate(1, (x) -> x + 4).limit(3);
        stream2.forEach(System.out::println); // 1 5 9
        // 随机获取三个随机数
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);


    }

}
