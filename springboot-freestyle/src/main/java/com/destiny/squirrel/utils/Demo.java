package com.destiny.squirrel.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-27 7:18 PM
 */
public class Demo {


    public static void main(String[] args) {

        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);
        ListNode listNode8 = new ListNode(8);
        ListNode listNode9 = new ListNode(9);
        ListNode listNode10 = new ListNode(10);
        ListNode listNode11 = new ListNode(11);
        ListNode listNode12 = new ListNode(12);
        ListNode listNode13 = new ListNode(13);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        listNode7.next = listNode8;
        listNode8.next = listNode9;
        listNode9.next = listNode10;
        listNode10.next = listNode11;
        listNode11.next = listNode12;
        listNode12.next = listNode13;

        // System.out.println(listNode1.toString());

        print1(listNode1, 2, 3);

        // ListNode listNode = reverseBetween(listNode1, 1, 13);
        // System.out.println(listNode);

        ListNode dd = createList();

        System.out.println(dd);


        String[] words = {"a", "ap", "app"};

    }

    public static void test001(String[] words) {

        Arrays.sort(words,(a,b) -> a.length() - b.length());


    }


    private static ListNode createList() {

        List<String> objects = Lists.newArrayList("4", "5", "6");

        ListNode list = new ListNode();
        ListNode pre = list;

        for (String node : objects) {
            pre.next = new ListNode(Integer.valueOf(node));
            pre = pre.next;
        }
        return list.next;

    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        // 设置 dummyNode 是这一类问题的一般做法
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        ListNode cur = pre.next;
        ListNode next;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummyNode.next;
    }

    public static void print1(ListNode head, int n, int m) {

        ListNode dumpy = head;
        ListNode cur = head;
        ListNode tail = dumpy;

        int cntN = n;
        int cntM = m;


        while (cur != null) {

            while (cur != null && cntN-- > 0) {
                tail = cur;
                cur = cur.next;
            }

            while (cur != null && cntM-- > 0) {
                cur = cur.next;
            }

            tail.next = cur;
            cntN = n;
            cntM = m;

        }

        System.out.println(dumpy);

    }

    public static void print(ListNode head, int n, int m) {


        int cnt = 1;
        int sum = n + m;

        ListNode result = new ListNode();
        ListNode cur = null;

        while (head != null) {
            if (cnt % sum <= n && cnt % sum > 0) {
                System.out.println(head.val);
                if (cnt == 1) {
                    result.val = head.val;
                    cur = new ListNode();
                    result.next = cur;
                } else {
                    cur.val = head.val;
                    cur.next = new ListNode();
                    cur = cur.next;
                }
            }
            head = head.next;
            cnt = cnt + 1;

        }


        int size = size(result);
        int cntt = 1;
        ListNode tmp = result;
        while (tmp != null) {
            if (cntt == size) {
                result.next = null;
                break;
            }
            tmp = tmp.next;
            result = result.next;
            cntt++;
        }

        System.out.println(result);

    }

    public static int size(ListNode tmp) {
        int len = 0;
        while (tmp != null) {
            len += 1;
            tmp = tmp.next;
        }
        return len;
    }


    public static class ListNode {

        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode() {
        }

        public ListNode(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {
            String result = "";
            ListNode head = this;
            while (head != null) {
                result += head.val + "- ";
                head = head.next;
            }
            return result;


        }
    }


}
