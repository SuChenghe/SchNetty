package com.suchenghe.study.reactor;

/**
 * @author SuChenghe
 * @date 2020/6/13 17:26
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 等待事件到来，分发事件处理
 */
class Reactor implements Runnable {

    public static Selector selector;
    public static ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定监听端口
        //backlog:输入连接指示（对连接的请求）的最大队列长度。如果队列满时收到连接指示，则拒绝该连接。
        serverSocketChannel.socket().bind(new InetSocketAddress(port), 1);
        //将channel注册到selector
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // attach Acceptor 处理新连接
        selectionKey.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    it.remove();
                    //分发事件处理
                    dispatch((SelectionKey) (it.next()));
                }
            }
        } catch (IOException ex) {
            //do something
        }
    }

    void dispatch(SelectionKey k) {
        // 若是连接事件获取是acceptor
        // 若是IO读写事件获取是handler
        Runnable runnable = (Runnable) (k.attachment());
        if (runnable != null) {
            runnable.run();
        }
    }

}
