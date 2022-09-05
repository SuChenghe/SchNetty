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
public class PromiseTest4 {
    public static void main(String[] args) {
        PromiseTest4 testPromise = new PromiseTest4();
        Promise<String> promise = testPromise.doSomething("哈哈");
        promise.addListener(future -> System.out.println(promise.get()+", something is done"));
    }

    /**
     * 创建一个DefaultPromise并返回，将业务逻辑放入线程池中执行
     * @param value
     * @return
     */
    private Promise<String> doSomething(String value) {
        NioEventLoopGroup loop = new NioEventLoopGroup();
        DefaultPromise<String> promise = new DefaultPromise<>(loop.next());

        loop.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    promise.setSuccess("执行成功。" + value);
                } catch (InterruptedException ignored) {
                    promise.setFailure(ignored);
                }
            }
        },0,3,TimeUnit.SECONDS);

//        loop.schedule(() -> {
//            try {
//                Thread.sleep(1000);
//                promise.setSuccess("执行成功。" + value);
//                return promise;
//            } catch (InterruptedException ignored) {
//                promise.setFailure(ignored);
//            }
//            return promise;
//        }, 1, TimeUnit.SECONDS);
        return promise;
    }
}
