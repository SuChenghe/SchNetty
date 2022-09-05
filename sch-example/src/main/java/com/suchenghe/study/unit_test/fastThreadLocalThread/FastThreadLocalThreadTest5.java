package com.suchenghe.study.unit_test.fastThreadLocalThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/11/2 14:21
 * Description: No Description
 */
public class FastThreadLocalThreadTest5 {

    public static void main(String[] args) {

        for (int i = 0; i < 32; i++) {
            FastThreadLocal<Integer> fastThreadLocal = new FastThreadLocal<>();
        }

        FastThreadLocal<Integer> fastThreadLocal1 = new FastThreadLocal<>();

        new FastThreadLocalThread(() -> {
            fastThreadLocal1.set(1000);
            System.out.println(Thread.currentThread().getName() + ":" + fastThreadLocal1.get());
        }, "fastThreadLocal1").start();
    }

}
