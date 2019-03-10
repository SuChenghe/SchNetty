package com.suchenghe.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SuChenghe
 * @date 2018/7/27 15:57
 */
@Service
public class UdpServer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UdpServerHandler.class);
    /**
     * Netty服务端-启用线程
     */
    public static ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new WgThreadFactory());
    @Autowired
    UdpServerHandler udpServerHandler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup group = new NioEventLoopGroup();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioDatagramChannel.class)
                            .localAddress(61005)
                            .option(ChannelOption.SO_BROADCAST, true)
                            .handler(udpServerHandler);
                    Channel channel = bootstrap.bind().sync().channel();
                    LOGGER.info("Netty服务端启动成功");
                    channel.closeFuture().await();
                } catch (Exception e) {
                    LOGGER.error("Netty服务端启动失败:" + e.getMessage().toString());
                } finally {
                    //退出，释放线程池资源
                    group.shutdownGracefully();
                }
            }
        });
    }

    /**
     * 自定义线程工厂
     */
    static class WgThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        WgThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "wg-netty-pool-" +
                    POOL_NUMBER.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
