package com.suchenghe.study.unit_test.fastThreadLocalThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/11/2 14:21
 * Description: No Description
 */
public class FastThreadLocalFastThreadTest {

    private static FastThreadLocal<Integer> fastThreadLocal = new FastThreadLocal<>();

    public static void main(String[] args) {

        FastThreadLocalThread fastThreadLocalThread1 = new FastThreadLocalThread(() -> {
            for (int i = 1; i < 1000; i++) {
                fastThreadLocal.set(i);
                System.out.println("1:"+Thread.currentThread().getName() + ":" + fastThreadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "fastThreadLocal1");
        fastThreadLocalThread1.start();

        FastThreadLocalThread fastThreadLocalThread2 = new FastThreadLocalThread(() -> {
            for (int i = 1; i < 1000; i++) {
                fastThreadLocal.set(i);
                System.out.println("2:"+Thread.currentThread().getName() + ":" + fastThreadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "fastThreadLocal2");
        fastThreadLocalThread2.start();

    }

}
