package com.suchenghe.study.reactor.mainAndsubReactor;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author SuChenghe
 * @date 2020/6/13 18:31
 */
public class MainTimeServer {

    public static Selector selector;
    public static ServerSocketChannel serverSocketChannel;

    public static void init(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定监听端口
        //backlog:输入连接指示（对连接的请求）的最大队列长度。如果队列满时收到连接指示，则拒绝该连接。
        serverSocketChannel.socket().bind(new InetSocketAddress(port), 1);
        //将channel注册到selector
       serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws Exception {
        init(61005);
        MultiWorkThreadAcceptor multiWorkThreadAcceptor = new MultiWorkThreadAcceptor();
        multiWorkThreadAcceptor.run();
    }
}
