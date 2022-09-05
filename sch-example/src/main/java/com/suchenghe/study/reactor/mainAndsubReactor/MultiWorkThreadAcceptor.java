package com.suchenghe.study.reactor.mainAndsubReactor;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuChenghe
 * @date 2020/6/13 21:01
 */
public class MultiWorkThreadAcceptor implements Runnable {

    // cpu线程数相同多work线程
    int workCount = Runtime.getRuntime().availableProcessors();
    SubReactor[] subReactorArray = new SubReactor[workCount];
    ExecutorService executorService = Executors.newFixedThreadPool(workCount);
    volatile int subReactorIndex = 0;

    public MultiWorkThreadAcceptor() {
        this.init();
    }

    public void init() {
        for (int i = 0; i < subReactorArray.length; i++) {
            try {
                SubReactor subReactor = new SubReactor();
                subReactorArray[i] = subReactor;
                System.out.println("启动SubReactor"+subReactor.toString());
                executorService.execute(subReactor);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                SocketChannel c = MainTimeServer.serverSocketChannel.accept();
                if (c != null) {// 注册读写
                    System.out.println("新Channel:"+c.toString());
                    synchronized (c) {
                        //顺序获取SubReactor，然后注册channel
                        SubReactor work = subReactorArray[subReactorIndex];
                        work.registerChannel(c);
                        subReactorIndex++;
                        if (subReactorIndex >= subReactorArray.length) {
                            subReactorIndex = 0;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
