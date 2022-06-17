package com.destiny.dog.learn.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.util.SerializationUtils;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * https://www.cnblogs.com/chdf/p/11466522.html
 * <p>
 * https://blog.csdn.net/u012702547/article/details/121356523
 * <p>
 * https://www.cnblogs.com/chdf/p/11466522.html
 *
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-28 8:17 PM
 */
public class BufferUtils {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        list.add("5");


        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("6");
        list2.add("2");

        // 并集
        Collection<String> union = CollectionUtil.union(list, list2);
        // 交集
        Collection<String> intersection = CollectionUtil.intersection(list, list2);
        // 交集的补集
        Collection<String> disjunction = CollectionUtil.disjunction(list, list2);
        // 差集
        Collection<String> subtract = CollectionUtil.subtract(list, list2);

        // 笛卡尔积
        List<List<String>> lists = Lists.cartesianProduct(list, list2);
        // 分页数据
        List<List<String>> partition = Lists.partition(list, 2);
        // 流处理
        List<String> transform = Lists.transform(list2, node -> node + "3");
        // join
        String join = StrUtil.join(",", list);

        // String s = IOUtils.toString(new FileInputStream(""), "UTF-8");
        // 序列化
        byte[] ddds = SerializationUtils.serialize("ddd");

        int i = Collections.binarySearch(list, "5");


        // 分配10字节大小内存空间
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 输出初始化后position的值
        System.out.println("初始化position : " + buffer.position());
        //输出初始化收limit的值
        System.out.println("初始化limit : " + buffer.limit());
        //输出初始化后capacity的值
        System.out.println("初始化capacity : " + buffer.capacity());
        //输出初始化后ByteBuffer内容
        printBuffer(buffer);

        //调用rewind()之前指针指向下标9即位置10,已经是最大容量
        //调用rewind()之后将指针移动到下标0即位置1
        buffer.rewind();
        System.out.println("position:" + buffer.position() + ",limit:" + buffer.limit() + ",capacity:" + buffer.capacity());
        //执行写入操作,指针会自动移动
        buffer.putChar('a');
        //输出指针position,指针指向下标2即位置3
        System.out.println("写入字符'a'后,position位置为:" + buffer.position());
        buffer.putChar('啊');
        //输出指针position,指针指向下标4即位置5
        System.out.println("写入字符'啊'后,position位置为:" + buffer.position());


        String property = System.getProperty("user.dir");
        System.out.println(property);


    }

    public static void printBuffer(ByteBuffer buffer) {

        //记录当前位置
        int position = buffer.position();
        //指针移动到0
        buffer.position(0);
        //循环输出每个字节内容
        for (int i = 0; i < buffer.limit(); i++) {
            //读取操作,指针会自动移动
            byte b = buffer.get();
            System.out.print(Integer.toHexString(b));
        }
        //指针再移动到标记位置
        buffer.position(position);
        System.out.println();
    }
}
