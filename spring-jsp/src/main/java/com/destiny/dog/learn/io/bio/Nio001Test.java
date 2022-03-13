package com.destiny.dog.learn.io.bio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description nio 代码
 */
public class Nio001Test {


    // https://www.cnblogs.com/demodashi/p/9453012.html

    public static void main(String[] args) throws IOException {

        int port = 9897;
        //先把通道打开
        ServerSocketChannel server = ServerSocketChannel.open();
        // 绑定端口
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);
        // Selector.open
        Selector selector = Selector.open();
        // 注册事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务已启动，监听端口是：" + port);
        // 轮询
        while (true) {
            // 轮询,查看当前的链接数量
            int wait = selector.select();
            if (wait == 0) continue; //如果没有链接接入，进入下一次循环

            // 取号，默认给他分配个号码（排队号码）
            Set<SelectionKey> keys = selector.selectedKeys();  // 可以通过这个方法，知道可用通道的集合
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                // 删除已经处理的内容，防止重复处理
                iterator.remove();
                //处理逻辑
                process(selector, key);
            }
        }
    }


    private static void process(Selector selector, SelectionKey key) throws IOException {
        //判断客户端确定已经可以准备接收信息
        if (key.isAcceptable()) {
            // 事件传过来的key
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            // 转换为客户端的通道
            SocketChannel client = server.accept();
            // 设置为非阻塞模式
            client.configureBlocking(false);
            // 向客户端发消息
            client.write(ByteBuffer.wrap("send message to client".getBytes()));
            //注册选择器，并设置为读取模式，收到一个连接请求
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端请求连接事件");
        }
        //处理来自客户端的数据读取请求
        if (key.isReadable()) {
            //返回该SelectionKey对应的 Channel，其中有数据需要读取
            SocketChannel client = (SocketChannel) key.channel();
            //往缓冲区读数据
            ByteBuffer buff = ByteBuffer.allocate(1024);
            client.read(buff);
            byte[] data = buff.array();
            String message = new String(data);
            System.out.println("receive" + message);
            // 返回信息
            ByteBuffer outBuf = ByteBuffer.wrap(("client.".concat(message)).getBytes());
            client.write(outBuf);
        }
    }

}
