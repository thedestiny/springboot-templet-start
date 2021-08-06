package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * @Description 解析数据
 * @Author xieyue
 * @Date 2021-07-26 9:22 AM
 */
public class AnalyzeDataUtils {

    private static final String BL = "    ";

    public static void main(String[] args) {

        String text = "";

        analyzeTNode(text);

    }


    private static TNode analyzeTNode(String text) {

        List<String> lst = new ArrayList<>();
        int arrLen = 0;

        for (String node : text.split("\n")) {
            if (node.contains("Anonymous in ")) {
                continue;
            }
            String name = node.replaceAll("\\(.*\\)", "");
            String tmp = name.replace(BL, "*");
            lst.add(tmp);
            int count = StrUtil.count(tmp, "*");
            if (count >= arrLen) {
                arrLen = count;
            }
        }

        TNode[] levelList = new TNode[arrLen + 1];
        String root = lst.get(0);

        // 根节点
        TNode tNode = formatNode(0, root);
        levelList[0] = tNode;

        for (int i = 1, len = lst.size(); i < len; i++) {
            String tmp = lst.get(i);
            int count = StrUtil.count(tmp, "*");
            String node = tmp.replace("*", "");
            TNode tmpNode = formatNode(count, node);
            TNode tNode1 = levelList[count - 1];
            levelList[count] = tmpNode;
            tNode1.addChild(tmpNode);
        }

        printNode(tNode, arrLen);
        return tNode;

    }

    /**
     * 组装节点信息
     */
    private static TNode formatNode(int count, String node) {
        TNode tmpNode = new TNode();
        tmpNode.setOrigin(node);
        tmpNode.setLevel(count);
        tmpNode.setMethod(node.split("\\.")[1]);
        tmpNode.setKlass(node.split("\\.")[0]);
        return tmpNode;
    }


    public static void printNode(TNode node, int arrLen) {

        String[] levelList = new String[arrLen + 1];
        Set<TNode> nodeSet = new HashSet<>();
        LinkedList<TNode> stack = new LinkedList<>();
        nodeSet.add(node);
        stack.add(node);

        while (CollUtil.isNotEmpty(stack)) {

            TNode cur = stack.pop();
            levelList[cur.getLevel()] = cur.getOrigin();

            if (CollUtil.isEmpty(cur.getChildList())) {
                printInvoke(levelList, cur.getLevel());
                continue;
            }

            for (TNode next : cur.getChildList()) {
                if (!nodeSet.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    nodeSet.add(next);
                    break;
                }
            }
        }

    }

    private static void printInvoke(String[] levelList, int level) {
        StringBuffer buffer = new StringBuffer();
        for (int num = 0; num <= level; num++) {
            buffer.append(levelList[num]).append("\t");
        }
        System.out.println(buffer.toString());
        // System.out.println(levelList[level]);
    }



}
