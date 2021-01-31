package com.destiny.dog.util;

import com.destiny.dog.entity.User;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {

	/**
	 * 下界通配符 < ? super E>
	 * 上界通配符 < ? extends E>
	 *
	* */
	
    public static void main(String[] args) {
        Predicate<Integer> predicate = x -> x > 185;
        User student = new User("9龙", 23, 175);
        System.out.println(
                "9龙的身高高于185吗？：" + predicate.test(student.getHeight()));

        Consumer<String> consumer = System.out::println;
        consumer.accept("命运由我不由天");
        // require response
	    Function<User, String> function1 = (node) -> node.getName() + "";
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

        // 4 flatMap
        User user1 = new User();
        
        user1.setAge(34);
        user1.setTags(Lists.newArrayList("23","34","45"));
        User user2 = new User();
        user2.setAge(12);
        user2.setTags(Lists.newArrayList("13","14","15"));
        
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
	    // 根据某些内容去重
        ArrayList<User> collect6 = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(node -> node.getAge()))), ArrayList::new));
	    
        
	    
	
	    List<String> collect4 = Stream.of(user1, user2).flatMap(node -> node.getTags().stream().map(String::intern)).collect(Collectors.toList());
        System.out.println(collect4);

        // 5 min max
        Optional<String> minVal = collect3.stream().distinct().min(Comparator.comparing(node -> node));
        if (minVal.isPresent()) {
            System.out.println(minVal.get());
        }


        // 6 count 统计值
        long count = collect1.stream().filter(node -> node > 50).count();

        // 7 reduce  初始值 20 累加结果
        Integer reduce = Stream.of(1, 2, 3, 4).reduce(20, (acc, x) -> acc + x);
        System.out.println(reduce);

        // 8 partitioningBy 按照属性进行分类
        Map<Boolean, List<User>> collect5 = Stream.of(user1, user2).collect(Collectors.partitioningBy(node -> node.getAge() > 20));


        // 9 Collectors.joining() 则是直接拼接
        String collect = Stream.of("1", "2", "3", "4").collect(Collectors.joining(",", "[", "]"));

        // 10 group by
        HashMap<String,String> map = new HashMap<>(30,0.74f);
        map.put("r","r");
        ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<>(30,0.74f);
        chm.put("d1","d");
        chm.put("d2","d");
        chm.put("d3","d");
        chm.put("d4","d");
	
	    ReentrantLock lock = new ReentrantLock();
	    lock.lock();
	    lock.lock();
     
     
    }
    
    
    
    
}

