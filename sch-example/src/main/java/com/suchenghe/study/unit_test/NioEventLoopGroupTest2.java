package com.suchenghe.study.unit_test;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/10/22 11:29
 * Description: No Description
 */
public class NioEventLoopGroupTest2 {

    public static void main(String[] args) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        EventLoop eventLoop = loop.next();

        for(;;){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventLoop.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+","+System.currentTimeMillis());
                }
            });
            eventLoop.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+",,,,,,,"+System.currentTimeMillis());
                }
            },0,3,TimeUnit.SECONDS);
        }

    }

}
