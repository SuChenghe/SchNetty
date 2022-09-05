package com.suchenghe.study.unit_test.fastThreadLocalThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/11/2 14:21
 * Description: No Description
 */
public class SingleThreadWithMultiFastThreadLocal2 {


    public static void main(String[] args) {

        new FastThreadLocalThread(() -> {
            for (int i = 1; i <= 33; i++) {
                FastThreadLocal<String> fastThreadLocal1 = new FastThreadLocal<>();
                fastThreadLocal1.set("value"+i);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(i == 10 || i == 20 || i == 30){
                    fastThreadLocal1.remove();
                }
            }
        }, "fastThreadLocal1").start();
    }

}
