package com.suchenghe.study.unit_test.recycler;

import io.netty.util.Recycler;

public class Recycler2MultipleRecycle {

    private static final Recycler<SchUser> RECYCLER = new Recycler<SchUser>() {
        @Override
        protected SchUser newObject(Handle<SchUser> handle) {
            SchUser schUser = new SchUser(handle);
            System.out.println(schUser);
            return schUser;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        try{
            SchUser schUser1 = RECYCLER.get();
            System.out.println(schUser1);
            schUser1.recycle();
            schUser1.recycle();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        System.out.println();
        try{
            SchUser schUser2 = RECYCLER.get();
            SchUser schUser3 = RECYCLER.get();
            System.out.println(schUser2);
            System.out.println(schUser3);
            schUser2.recycle();
            schUser3.recycle();
            schUser2.recycle();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        System.out.println();
        try{
            SchUser schUser1 = RECYCLER.get();
            System.out.println(schUser1);
            schUser1.recycle();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    schUser1.recycle();
                }
            }).start();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private static class SchUser {
        private final Recycler.Handle<SchUser> handle;

        public SchUser(Recycler.Handle<SchUser> handle) {
            this.handle = handle;
        }

        public void recycle(){
            handle.recycle(this);
        }
    }

}
