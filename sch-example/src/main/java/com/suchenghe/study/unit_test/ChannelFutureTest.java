package com.suchenghe.study.unit_test;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.TimeUnit;

public class ChannelFutureTest {

    public static void main(String[] args) throws InterruptedException {

        NioServerSocketChannel channel = new NioServerSocketChannel();

        DefaultEventLoop defaultEventLoop = new DefaultEventLoop();

        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise(channel,defaultEventLoop);

        for (int i = 0; i < 100000; i++) {
            final int num = i;
            defaultChannelPromise.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println(Thread.currentThread()+":"+future.toString()+":"+num);
                }
            });
        }

        TimeUnit.SECONDS.sleep(2);

        defaultChannelPromise.setSuccess();

    }

}
