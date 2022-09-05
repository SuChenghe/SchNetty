package com.suchenghe.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author SuChenghe
 * @date 2018/10/28 11:04
 */
public class Timeserver2 implements Runnable {
    /**
     * 通道
     */
    private ServerSocketChannel serverSocketChannel;

    @Override
    public void run() {
        while (true) {
            try {
                SocketChannel socket = serverSocketChannel.accept();
                if (socket != null) {
                    System.out.println("接收到SocketChannel" + socket.getRemoteAddress().toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public Timeserver2(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            //非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定监听端口
            //backlog:输入连接指示（对连接的请求）的最大队列长度。如果队列满时收到连接指示，则拒绝该连接。
            serverSocketChannel.bind(new InetSocketAddress(port), 1);
            //将channel注册到selector
            System.out.println("Timeserver2,port:" + port + "已启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Timeserver2 timeserver = new Timeserver2(61006);
        timeserver.run();
    }
}
