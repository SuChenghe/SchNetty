package com.suchenghe.study.unit_test.fastThreadLocalThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/11/2 14:21
 * Description: No Description
 */
public class FastThreadLocalNormalThreadTest {

    private static FastThreadLocal<Integer> fastThreadLocal = new FastThreadLocal<>();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 1000; i++) {
                fastThreadLocal.set(i*1000);
                System.out.println("thread1:"+Thread.currentThread().getName() + ":" + fastThreadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread1");
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 1; i < 1000; i++) {
                fastThreadLocal.set(i*10000);
                System.out.println("thread2:"+Thread.currentThread().getName() + ":" + fastThreadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "thread2");
        thread2.start();

    }

}
