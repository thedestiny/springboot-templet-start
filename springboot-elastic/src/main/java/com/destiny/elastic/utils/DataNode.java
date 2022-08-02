package com.destiny.elastic.utils;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 表达式树
 * @Date 2022-08-02 10:03 AM
 */

@Slf4j
public class DataNode {


    public static void main(String[] args) {
        DataNode.Formaluetree tree = new DataNode.Formaluetree();
        tree.creatTree("45+23*56/2-5");//创建表达式的二叉树
        tree.output();//输出验证

    }


    public static ExprNode createTree(String expression) {

        LinkedList<ExprNode> stack = new LinkedList<>();

        // 存放数据
        List<String> dataList = new ArrayList<>();
        String s = "";
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i); //取出字符串的各个字符
            if (ch >= '0' && ch <= '9') {
                s += ch;
            } else {
                dataList.add(s);
                s = "";
                dataList.add(String.valueOf(ch));
            }
        }

        for (int i = 0; i < dataList.size(); i++) {
            String ch = dataList.get(i);
            // ch != '+' && ch != '-' && ch != '*' && ch != '/'
            if (!StrUtil.equalsAny(ch, "+", "-", "*", "/")) {
                ExprNode node = new ExprNode(ch);
                stack.push(node);
            } else {
                ExprNode node = new ExprNode(ch);
                ExprNode rightNode = stack.pop();
                ExprNode leftNode = stack.pop();
                node.setLeft(leftNode);
                node.setRight(rightNode);
                stack.push(node);
            }

        }
        return stack.pop();

    }


    // 表达式树
    @Data
    static class ExprNode {

        // 数据值
        private String data;
        // 左子树
        private ExprNode left;
        // 右子树
        private ExprNode right;


        ExprNode(String data) {
            this.data = data;
        }

        ExprNode(String data, ExprNode left, ExprNode right) {

            this.data = data;
            this.left = left;
            this.right = right;

        }

    }


    @Data
    static class Formaluetree {

        private String s = "";
        private ExprNode root; //根节点

        public Formaluetree() {
        }

        /**
         * 创建表达式二叉树
         *
         * @param str 表达式
         */

        public void creatTree(String str) {

            //声明一个数组列表，存放的操作符，加减乘除
            List<ExprNode> operList = new ArrayList<ExprNode>();

            //声明一个数组列表，存放节点的数据
            List<ExprNode> numList = new ArrayList<ExprNode>();

            //第一，辨析出操作符与数据，存放在相应的列表中
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i); //取出字符串的各个字符
                if (ch >= '0' && ch <= '9') {
                    s += ch;
                } else {
                    numList.add(new ExprNode(s));
                    s = "";
                    operList.add(new ExprNode(String.valueOf(ch)));
                }
            }
            //把最后的数字加入到数字节点中
            numList.add(new ExprNode(s));
            while (operList.size() > 0) { //第三步，重复第二步，直到操作符取完为止
                //第二，取出前两个数字和一个操作符，组成一个新的数字节点
                ExprNode left = numList.remove(0);
                ExprNode right = numList.remove(0);
                ExprNode oper = operList.remove(0);
                ExprNode node = new ExprNode(oper.getData(), left, right);
                numList.add(0, node); //将新生的节点作为第一个节点，同时以前index=0的节点变为index=1
            }

            //第四步，让根节点等于最后一个节点
            root = numList.get(0);


        }

        /**
         * 输出结点数据
         */

        public void output() {

            output(root); //从根节点开始遍历

        }

        /**
         * 输出结点数据
         *
         * @param node
         */

        public void output(ExprNode node) {

            if (node.getLeft() != null) { //如果是叶子节点就会终止
                output(node.getLeft());
            }

            System.out.print(node.getData()); //遍历包括先序遍历(根左右)、中序遍历(左根右)、后序遍历(左右根)
            if (node.getRight() != null) {
                output(node.getRight());

            }

        }


    }


}