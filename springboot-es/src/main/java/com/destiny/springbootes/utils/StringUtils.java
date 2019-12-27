package com.destiny.springbootes.utils;

public class StringUtils {


    public static char getRandomChar() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }

}
