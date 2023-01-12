package com.destiny.cormorant.exception;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Description
 * @Author destiny
 * @Date 2021-10-14 6:20 PM
 */
@Data
public class DataAccessException extends RuntimeException {

    public static void main(String[] args) throws UnsupportedEncodingException {

        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = "字串文字";
        final byte[] textByte = text.getBytes("UTF-8");
//编码
        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
//解码
        System.out.println(new String(decoder.decode(encodedText), "UTF-8"));

        final Base64.Decoder decoder1 = Base64.getDecoder();
        final Base64.Encoder encoder1 = Base64.getEncoder();
        final String text1 = "字串文字";
        final byte[] textByte1 = text1.getBytes("UTF-8");
//编码
        final String encodedText1 = encoder1.encodeToString(textByte1);
        System.out.println(encodedText1);
//解码
        System.out.println(new String(decoder1.decode(encodedText1), "UTF-8"));
        String tt = "c2VsZWN0IChzZWxlY3QgY291bnQoKikgZnJvbSB0X3hmX2FjY291bnQpIGFzICcxLjDmtojotLnotKbmiLfmlbAnICwgKHNlbGVjdCBjb3VudCgqKSBmcm9tIGxmX2FjY291bnQgd2hlcmUgYWNjb3VudF90eXBlID0gIjEiICkgYXMgJzIuMOa2iOi0uei0puaIt+aVsCc7";
        System.out.println(new String(decoder1.decode(tt), "UTF-8"));
    }
}
