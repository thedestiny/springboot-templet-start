package com.destiny.origin.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-11-26 4:51 PM
 */
public class NettyServer {

    public static void main(String[] args) {

        boolean b = StrUtil.equalsAny(null, "4");
        System.out.println(b);

        Map<String, String> zip = CollUtil.zip("55,34,45", "44,4,5", ",");
        System.out.println(zip);
        List<String> idList = new ArrayList<>();
        idList.add("3");
        idList.add("4");
        idList.add("5");
        String join = CollUtil.join(idList, "-");
        System.out.println(join);

    }

}
