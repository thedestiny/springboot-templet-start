package com.destiny.squirrel.utils;

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

        System.out.println(listNode1.toString());

        print(listNode1, 2, 3);

    }


    public static void print(ListNode head, int n, int m) {


        int len = 0;
        ListNode tmp = head;
        while (tmp != null) {
            len += 1;
            tmp = tmp.next;
        }

        int cnt = 1;
        int sum = n + m;

        ListNode result = new ListNode();
        ListNode cur = null;
        ListNode tail = null;

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

        cur = null;

        System.out.println(result);

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
                result += head.val + "-";
                head = head.next;
            }
            return result;


        }
    }


}
