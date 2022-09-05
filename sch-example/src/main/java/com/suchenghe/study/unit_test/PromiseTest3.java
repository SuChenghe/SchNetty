package com.suchenghe.study.unit_test;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/10/22 9:48
 * Description: No Description
 */
public class PromiseTest3 {
    public static void main(String[] args) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        DefaultPromise<String> promise = new DefaultPromise<>(loop.next());

        promise.addListener(future -> System.out.println(promise.get()+",This is First"));

        loop.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始执行");
                promise.setSuccess("执行成功。" + System.currentTimeMillis());
//                promise.addListener(future -> System.out.println(promise.get()+",This is next"));
                System.out.println("结束执行");
            }
        });

    }

}
