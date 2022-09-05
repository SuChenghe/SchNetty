package com.suchenghe.study.reactor;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
/**
 * @author SuChenghe
 * @date 2020/6/13 17:46
 * 连接事件就绪,处理连接事件
 */

class Acceptor implements Runnable {

    @Override
    public void run() {
        try {
            SocketChannel c = Reactor.serverSocketChannel.accept();
            if (c != null) {// 注册读写
                new Handler(c, Reactor.selector);
            }
        } catch (Exception e) {

        }
    }
}
