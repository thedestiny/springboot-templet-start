package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xieyue
 * @Date 2021-07-26 9:28 AM
 */
@Data
public class TNode {

    private int level;
    private String name;
    private String klass;
    private String method;
    private String pack;
    private String origin;

    private List<TNode> childList;


    public void addChild(TNode node) {
        if (CollUtil.isEmpty(childList)) {
            childList = new ArrayList<>();
        }
        childList.add(node);
    }


}
