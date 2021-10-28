package com.destiny.squirrel.utils;

/**
 * @Description 进制转换工具类
 * @Author liangwenchao
 * @Date 2021-10-28 8:51 PM
 */
public class HexUtils {


    // https://blog.csdn.net/qq_32711309/article/details/83783141

    private static final char HEX_CHAR_ARR[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String HEX_STR = "0123456789abcdef";

    /**
     * 二进制数组转 16进制字符串
     */
    public static String byteArrToHex(byte[] btArr) {
        char strArr[] = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HEX_CHAR_ARR[bt >>> 4 & 0xf];
            strArr[i++] = HEX_CHAR_ARR[bt & 0xf];
        }
        return new String(strArr);
    }

    /**
     * 16进制字符串 转 二进制数组
     */
    public static byte[] hexToByteArr(String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte btArr[] = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HEX_STR.indexOf(charArr[i]);
            int lowBit = HEX_STR.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }

}
