package com.suchenghe.study.unit_test.recycler;

import io.netty.util.Recycler;

import java.util.concurrent.atomic.AtomicInteger;

public class RecyclerNewObject {

    private static ThreadLocal<AtomicInteger> seqNumThreadLocal = new ThreadLocal<AtomicInteger>(){
        public AtomicInteger initialValue(){
            return new AtomicInteger(0) ;
        }
    };

    private static final Recycler<SchUser> RECYCLER = new Recycler<SchUser>() {
        @Override
        protected SchUser newObject(Handle<SchUser> handle) {
            SchUser schUser = new SchUser(handle);
            schUser.setName(Thread.currentThread().getName() + "&" + seqNumThreadLocal.get().incrementAndGet());
            return schUser;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        int num = 0;
        while (true){
            SchUser schUser = RECYCLER.get();
            System.out.println(Thread.currentThread().getName() + " ; get() ; " + schUser.getName());
            if(num ++ == 50){
                break;
            }
        }
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
