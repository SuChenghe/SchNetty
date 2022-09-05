package com.suchenghe.study.unit_test.fastThreadLocalThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/11/2 14:21
 * Description: No Description
 */
public class SingleThreadWithMultiFastThreadLocal {

    private static FastThreadLocal<Integer> fastThreadLocal1 = new FastThreadLocal<>();
    private static FastThreadLocal<Integer> fastThreadLocal2 = new FastThreadLocal<>();
    private static FastThreadLocal<Integer> fastThreadLocal3 = new FastThreadLocal<>();

    public static void main(String[] args) {

        new FastThreadLocalThread(() -> {
            for (int i = 1; i < 1000; i++) {
                fastThreadLocal1.set(i*1000);
                fastThreadLocal3.set(i*2000);
                System.out.println("1:"+Thread.currentThread().getName()
                        + ":" + fastThreadLocal1.get() + ":" + fastThreadLocal2.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "fastThreadLocal1").start();
    }

}
