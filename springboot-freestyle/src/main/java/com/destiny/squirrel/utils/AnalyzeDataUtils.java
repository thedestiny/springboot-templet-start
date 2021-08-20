package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 解析数据
 * @Author xieyue
 * @Date 2021-07-26 9:22 AM
 */
public class AnalyzeDataUtils {

    private static final String BL = "    ";

    public static void main(String[] args) {

        // 读取文件数据
        BufferedReader readerData = ResourceUtil.getUtf8Reader("test_data.txt");
        // 读取文件
        String read = IoUtil.read(readerData);
        // System.out.println(read);
        analyzeTNode(read);

    }

    /**
     * 匹配包路径
     */
    private static String matchPack(String text) {

        // String str = "中华人民共和国，简称（中国）。";
        // Matcher mat = Pattern.compile("(?<=\\（)(\\S+)(?=\\）)").matcher(text); //此处是中文输入的（）
        Pattern compile = Pattern.compile("(?<=\\()(\\S+)(?=\\))");
        Matcher mat = compile.matcher(text); //此处是中文输入的（）
        List<String> result = new ArrayList<>();
        while (mat.find()) {
            result.add(mat.group());
        }
        if (CollUtil.isNotEmpty(result)) {
            return result.get(result.size() - 1);
        }
        return "";
    }


    private static TNode analyzeTNode(String text) {

        List<String> lst = new ArrayList<>();
        int arrLen = 0;

        for (String node : text.split("\n")) {
            String tmp = node.replace(BL, "*");
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
        String name = node.replaceAll("\\(.*\\)", "");
        tmpNode.setName(name);
        tmpNode.setLevel(count);
        tmpNode.setMethod(name.split("\\.")[1]);
        tmpNode.setKlass(name.split("\\.")[0]);
        tmpNode.setPack(matchPack(node));
        tmpNode.setOrigin(node);
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
            levelList[cur.getLevel()] = cur.getName();

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

        String output = buffer.toString();
        String sub = StrUtil.sub(output, 0, output.length() - 1);

        if(!sub.contains("Test.")){
            System.out.println(sub);
        }
        // System.out.println(buffer.toString());
        // System.out.println(levelList[level]);
    }


}
