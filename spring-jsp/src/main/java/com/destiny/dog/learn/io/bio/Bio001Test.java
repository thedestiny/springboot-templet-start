package com.destiny.dog.learn.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description
 * @Author destiny
 * @Date 2022-03-07 2:53 PM
 */
public class Bio001Test {

    public static void main(String[] args) throws IOException {

        //把Socket服务端启动
        ServerSocket server = new ServerSocket(8986);
        while (true) {
            // 阻塞方法,等待客户端的接入
            Socket client = server.accept();
            // 得到输入流
            InputStream input = client.getInputStream();
            // 建立缓冲区
            byte[] buff = new byte[4096];
            int len = input.read(buff);
            // 只要一直有数据写入，len就会一直大于0
            if (len > 0) {
                String msg = new String(buff, 0, len);
                System.out.println("收到" + msg);
            }
        }


    }

}
