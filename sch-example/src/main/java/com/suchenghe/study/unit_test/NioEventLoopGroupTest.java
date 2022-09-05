package com.suchenghe.study.unit_test;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/10/22 11:29
 * Description: No Description
 */
public class NioEventLoopGroupTest {

    public static void main(String[] args) {
        //private static final int DEFAULT_EVENT_LOOP_THREADS;
        //
        //    static {
        //        DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
        //                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        //
        //        if (logger.isDebugEnabled()) {
        //            logger.debug("-Dio.netty.eventLoopThreads: {}", DEFAULT_EVENT_LOOP_THREADS);
        //        }
        //    }
        NioEventLoopGroup loop = new NioEventLoopGroup();
        for (int i = 0; i < 10; i++) {
            EventLoop eventLoop = loop.next();

            eventLoop.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });

            System.out.println(eventLoop);
        }

        loop.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },1, TimeUnit.SECONDS);

    }

}
