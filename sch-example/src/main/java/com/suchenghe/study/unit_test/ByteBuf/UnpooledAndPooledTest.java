package com.suchenghe.study.unit_test.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @author SuChenghe
 * @date 2020/3/26 17:31
 * Description: No Description
 */
public class UnpooledAndPooledTest {
    public static void main(String[] args) throws InterruptedException {
        pooledHeapTest();
    }

    /**
     * 垃圾回收次数8次，回收时间31毫秒
     * 执行耗时13954毫秒
     */
    private static void pooledHeapTest(){
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator(false);
        long beginTime = System.currentTimeMillis();
        System.out.println("开始执行:"+beginTime);
        ByteBuf byteBuf = null;
        int maxTimes = 100000000;
        for (int i = 0; i < maxTimes; i++) {
            byteBuf = pooledByteBufAllocator.heapBuffer(10*1024);
            byteBuf.release();
        }
        System.out.println("耗时:"+(System.currentTimeMillis()-beginTime));
    }

    /**
     * 垃圾回收次数7次，回收时间31毫秒
     * 执行耗时11451毫秒
     */
    private static void pooledDirectTest(){
        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator(true);
        long beginTime = System.currentTimeMillis();
        System.out.println("开始执行:"+beginTime);
        ByteBuf byteBuf = null;
        int maxTimes = 100000000;
        for (int i = 0; i < maxTimes; i++) {
            byteBuf = pooledByteBufAllocator.heapBuffer(10*1024);
            byteBuf.release();
        }
        System.out.println("耗时:"+(System.currentTimeMillis()-beginTime));
    }

    /**
     * 垃圾回收次数1060次，回收时间960毫秒
     */
    private static void unpooledTest(){
        long beginTime = System.currentTimeMillis();
        System.out.println("开始执行:"+beginTime);
        ByteBuf byteBuf = null;
        int maxTimes = 100000000;
        for (int i = 0; i < maxTimes; i++) {
            byteBuf = Unpooled.buffer(10*1024);
            byteBuf.release();
        }
        System.out.println("耗时:"+(System.currentTimeMillis()-beginTime));
    }

}
