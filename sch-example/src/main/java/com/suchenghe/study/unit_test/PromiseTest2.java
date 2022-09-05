package com.suchenghe.study.unit_test;

import io.netty.util.concurrent.*;

/**
 * @author SuChenghe
 * @date 2020/10/21 16:58
 * Description: No Description
 */
public class PromiseTest2 {

    public static void main(String[] args) {
        // 构造线程池
        EventExecutor executor = new DefaultEventExecutor();

        Promise promise = new DefaultPromise(executor);

        //提交任务到线程池，设置执行promise的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                promise.setFailure(new RuntimeException());
            }
        });

        // main 线程阻塞等待执行结果
        try {
            promise.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("关闭线程池...");
        executor.shutdownGracefully();

    }
}