package com.platform.itcast.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KmpUtils {


    public static void main(String[] args) {

        String str1 = "ABACADBCABCD";
        String pattern = "ABCD";

        int[] next = new int[str1.length()];

        getNext(str1.toCharArray(), next);

        int search = search(str1.toCharArray(), pattern.toCharArray(), next);
        System.out.println(search);


    }

    public static int search(char[] str, char[] pattern, int[] next) {

        int i = 0;
        int j = 0;

        while (i < str.length && j < pattern.length) {
            if (j == -1 || str[i] == pattern[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == pattern.length) {
            return i - j;
        } else {
            return -1;
        }
    }


    public static void getNext(char[] pattern, int[] next) {

        next[0] = -1;
        int i = 0, j = -1;

        while (i < pattern.length) {
            if (j == -1) {
                i++;
                j++;
            } else if (pattern[i] == pattern[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }

        }

    }


}
