package com.suchenghe.study.unit_test;

import java.util.concurrent.Executor;

/**
 * @author SuChenghe
 * @date 2020/10/22 16:21
 * Description: No Description
 */
public class ExecutorTest {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("11111"+Thread.currentThread().getName());
            }
        };
        runnable.run();


        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                System.out.println("33333"+Thread.currentThread().getName());
            }
        };
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("44444"+Thread.currentThread().getName());
            }
        });

    }

}
