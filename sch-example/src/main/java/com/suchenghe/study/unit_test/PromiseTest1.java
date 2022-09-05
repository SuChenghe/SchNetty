package com.suchenghe.study.unit_test;

import io.netty.util.concurrent.*;

/**
 * @author SuChenghe
 * @date 2020/10/21 16:58
 * Description: No Description
 */
public class PromiseTest1 {

    public static void main(String[] args) {
        // 构造线程池
        EventExecutor executor = new DefaultEventExecutor();

        Promise promise = new DefaultPromise(executor);

        //下面给这个 promise 添加两个 listener
        promise.addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println(Thread.currentThread().getName()+"任务结束，结果：" + future.get());
                } else {
                    System.out.println(Thread.currentThread().getName()+"任务失败，异常：" + future.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println(Thread.currentThread().getName()+"任务结束...");
            }
        });

        //提交任务到线程池，设置执行promise的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"执行线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                promise.setSuccess("成功");
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
