package com.suchenghe.study.unit_test.recycler;

import io.netty.util.Recycler;
import lombok.SneakyThrows;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RecyclerMultiple {

    private static ThreadLocal<AtomicInteger> seqNumThreadLocal = new ThreadLocal<AtomicInteger>(){
        public AtomicInteger initialValue(){
            return new AtomicInteger(0) ;
        }
    };

    private static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);
    private static ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(1);
    private static ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(1);

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
        fixedThreadPool1.execute(new Runnable() {
            @Override
            public void run() {
                int getNum = 0;
                while (true){
                    if(getNum ++ <= 50){
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
        System.out.println();

        fixedThreadPool2.execute(new Runnable() {
            @Override
            public void run() {
                SchUser schUser = RECYCLER.get();
                System.out.println(Thread.currentThread().getName() + " ; get() ;" +schUser.getName());
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        fixedThreadPool3.execute(new Runnable() {
            @Override
            public void run() {
                SchUser schUser = commonSchUserQueue.poll();
                schUser.recycle();
                System.out.println(Thread.currentThread().getName() + " ; recycle ;" +schUser.getName());
                schUser = RECYCLER.get();
                System.out.println(Thread.currentThread().getName() + " ; get() ;" +schUser.getName());
                int getNum = 0;
                while (true){
                    if(getNum ++ <= 49){
                        schUser = commonSchUserQueue.poll();
                        schUser.recycle();
                        System.out.println(Thread.currentThread().getName() + " ; recycle ;" +schUser.getName());
                    }else{
                        break;
                    }
                }
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println();

        fixedThreadPool1.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    SchUser schUser = RECYCLER.get();
                    System.out.println(Thread.currentThread().getName() + " ; get() ;" +schUser.getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
