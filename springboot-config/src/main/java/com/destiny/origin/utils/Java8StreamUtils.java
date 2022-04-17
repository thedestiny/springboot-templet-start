package com.destiny.origin.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Java8StreamUtils {

    public static void main(String[] args) {

        String test = "222";
        int length = test.length();
        int i = test.codePointCount(0, length);
        System.out.println(i);



        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        Integer[] nums = new Integer[10];

        Stream<Integer> stream1 = Arrays.stream(nums);

        // of 创建 stream
        Stream<String> strs = Stream.of("a", "b", "c", "d", "d", "e");
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

        // 标签
        List<String> tags1 = Lists.newArrayList("a", "b", "c");
        List<String> tags2 = Lists.newArrayList("d", "e", "f");
        // 创建对象
        Student student1 = new Student("小明", 12, tags1, BigDecimal.valueOf(43));
        Student student2 = new Student("小李", 14, tags2, BigDecimal.valueOf(43));
        // 声明数组对象
        List<Student> studentList = Lists.newArrayList(student1, student2);
        // 年龄和体重数据
        List<Integer> ageList = studentList.stream().map(Student::getAge).collect(Collectors.toList());
        Set<BigDecimal> weightSet = studentList.stream().map(Student::getWeight).collect(Collectors.toSet());
        // 建立姓名年龄映射
        Map<String, Integer> nameAgeMap = studentList.stream().collect(Collectors.toMap(Student::getName, Student::getAge, (k1, k2) -> k2));
        // flatMap 获取所有的标签
        List<String> tagsList = studentList.stream().flatMap(node -> node.getTags().stream().map(String::intern)).distinct().collect(Collectors.toList());

        // 按照年龄分组
        Map<Integer, List<Student>> ageMap = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        // 分区分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<Student>> partMap = studentList.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));
        //规约
        Integer sumAge = studentList.stream().map(Student::getAge).collect(Collectors.reducing(Integer::sum)).get();

    }

}
