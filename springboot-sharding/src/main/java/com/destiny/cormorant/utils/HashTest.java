package com.destiny.cormorant.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Date 2022-08-14 9:40 PM
 */
@Data
@Slf4j
public class HashTest {

    private Integer age;
    private String name;

    public static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
		/*这里面为啥用个31来计算，而且很多人都是这么写的，
		这是因为31是个神奇的数字，任何数n*31都可以被jvm优化为(n<<5)-n，
		移位和减法的操作效率比乘法的操作效率高很多！*/
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        //当这个域的值为null的时候（即 string == null 时），那么hashCode值为0
        result = prime * result + age;
        return result;
    }

    public static void main(String[] args) {

        HashTest obj = new HashTest();
        obj.setAge(12);
        obj.setName("小李");
        log.info("hash {}" , obj.hashCode());
        // identityHashCode 对象内存地址返回一个hash值, 不管对象是否重载了hashCode方法，
        // identityHashCode方法都会返回该类默认 hashCode 方法会返回的值
        int i = System.identityHashCode(obj);
        log.info("identityHashCode {}", i);
        log.info("toHexString {}", Integer.toHexString(i));
        log.info("hash {}", hash(obj));


    }




}
