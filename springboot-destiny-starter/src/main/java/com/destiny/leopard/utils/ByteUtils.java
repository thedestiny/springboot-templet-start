package com.destiny.leopard.utils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author destiny
 * @Date 2022-07-05 10:05 PM
 *
 * websocket
 * https://www.csdn.net/tags/OtTaYgxsNzYyODctYmxvZwO0O0OO0O0O.html
 */
public class ByteUtils {

    // https://blog.csdn.net/GoNewWay/article/details/106507722

    public static void main(String[] args) {
        Charset charset = Charset.forName("utf-8");

        ByteBuffer dd = charset.encode("dd");

        ByteBuffer buffer1 = charset.encode("你好");
        ByteBuffer buffer2 = Charset.forName("utf-8").encode("你好");

        CharBuffer buffer3 = charset.decode(buffer1);
        System.out.println(buffer3.getClass());
        System.out.println(buffer3.toString());


        BigDecimal decimal = BigDecimal.valueOf(44).setScale(4, BigDecimal.ROUND_HALF_UP);



    }

}
