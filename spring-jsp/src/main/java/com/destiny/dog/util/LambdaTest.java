package com.destiny.dog.util;

import com.destiny.dog.entity.User;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {

    public static void main(String[] args) {
        Predicate<Integer> predicate = x -> x > 185;
        User student = new User("9龙", 23, 175);
        System.out.println(
                "9龙的身高高于185吗？：" + predicate.test(student.getHeight()));

        Consumer<String> consumer = System.out::println;
        consumer.accept("命运由我不由天");

        Function<User, String> function = User::getName;
        String name = function.apply(student);
        System.out.println(name);

        Supplier<Integer> supplier =
                () -> Integer.valueOf(BigDecimal.TEN.toString());
        System.out.println(supplier.get());

        UnaryOperator<Boolean> unaryOperator = uglily -> !uglily;
        Boolean apply2 = unaryOperator.apply(true);
        System.out.println(apply2);

        BinaryOperator<Integer> operator = (x, y) -> x * y;
        Integer integer = operator.apply(2, 3);
        System.out.println(integer);

        test(() -> "我是一个演示的函数式接口");

        test001();


    }

    /**
     * 演示自定义函数式接口使用
     *
     * @param worker
     */
    public static void test(Worker worker) {
        String work = worker.work();
        System.out.println(work);
    }

    public interface Worker {
        String work();
    }

    /**
     * 测试接口
     * */
    public static void test001(){

        // 1 collect(Collectors.toList()) collect(Collectors.toSet())
        List<Integer> collect1 = Stream.of(23, 34, 56, 67).collect(Collectors.toList());

        // 2 filter
        List<Integer> collect2 = collect1.stream().filter(node -> node > 3).collect(Collectors.toList());

        // 3 map
        List<String> collect3 = collect2.stream().map(String::valueOf).collect(Collectors.toList());



        // 4 fiatMap

        // 5 min max
        String minVal = collect3.stream().distinct().min(Comparator.comparing(node -> node)).get();

        // 6 count 统计值
        long count = collect1.stream().filter(node -> node > 50).count();

        // 7 reduce  初始值 20 累加结果
        Integer reduce = Stream.of(1, 2, 3, 4).reduce(20, (acc, x) -> acc+ x);
        System.out.println(reduce);

        // 8 partitioningBy

        // 9 Collectors.joining() 则是直接拼接
        String collect = Stream.of("1", "2", "3", "4").collect(Collectors.joining(",", "[", "]"));

        // 10 group by

        HashMap<String,String> map = new HashMap<>(30,0.74f);

    }
}
