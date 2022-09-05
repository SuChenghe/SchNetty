package com.suchenghe.study.unit_test.recycler;

import io.netty.util.Recycler;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RecyclerWeakOrderQueue {

    private static ThreadLocal<AtomicInteger> seqNumThreadLocal = new ThreadLocal<AtomicInteger>(){
        public AtomicInteger initialValue(){
            return new AtomicInteger(0) ;
        }
    };

    private static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);
    private static ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(1);
    private static ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(1);
    private static ExecutorService fixedThreadPool4 = Executors.newFixedThreadPool(1);

    private static ArrayBlockingQueue<SchUser> commonSchUserQueue = new ArrayBlockingQueue<>(100000);

    private static final Recycler<SchUser> RECYCLER = new Recycler<SchUser>() {
        @Override
        protected SchUser newObject(Handle<SchUser> handle) {
            SchUser schUser = new SchUser(handle);
            schUser.setName(Thread.currentThread().getName() + "&" + seqNumThreadLocal.get().incrementAndGet());
            return schUser;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        //第1步,线程1创建对象
        fixedThreadPool1.execute(new Runnable() {
            @Override
            public void run() {
                int getNum = 1;
                while (true){
                    if(getNum ++ <= 100){
                        SchUser schUser = RECYCLER.get();
                        commonSchUserQueue.offer(schUser);
                        System.out.println(Thread.currentThread().getName() + " ; offer ; " +schUser.getName());
                    }else{
                        break;
                    }
                }
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //第2步,线程2、3、4回收对象
        fixedThreadPool2.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    SchUser schUser = commonSchUserQueue.take();
                    schUser.recycle();
                    System.out.println(Thread.currentThread().getName() + " ; recycle ;" +schUser.getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fixedThreadPool3.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    SchUser schUser = commonSchUserQueue.take();
                    schUser.recycle();
                    System.out.println(Thread.currentThread().getName() + " ; recycle ;" +schUser.getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fixedThreadPool4.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    SchUser schUser = commonSchUserQueue.take();
                    schUser.recycle();
                    System.out.println(Thread.currentThread().getName() + " ; recycle ;" +schUser.getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TimeUnit.SECONDS.sleep(3);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //第3步,线程3获取对象
        fixedThreadPool1.execute(new Runnable() {
            @Override
            public void run() {
                int getNum = 1;
                while (true){
                    if(getNum ++ <= 101){
                        SchUser schUser = RECYCLER.get();
                        System.out.println(Thread.currentThread().getName() + " ; get() ;" +schUser.getName());
                    }else{
                        break;
                    }
                }
            }
        });

    }

    private static class SchUser {
        private final Recycler.Handle<SchUser> handle;
        private String name;

        public SchUser(Recycler.Handle<SchUser> handle) {
            this.handle = handle;
        }

        public void recycle(){
            handle.recycle(this);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
