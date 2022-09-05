package com.suchenghe.study.example_decode_encode.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 17:00
 */
public class MyClient {

    /**
     * 定时任务线程
     */
    public static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    private final String host;
    private final int port;

    public MyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        //配置客户端NIO线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SchClientHandler());
                        }
                    });
            //发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //等待客户端链路断开
            channelFuture.channel().closeFuture().sync();

        } finally {
            //退出，释放线程池资源
            eventLoopGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyClient("127.0.0.1",61005).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0L,5L, TimeUnit.SECONDS);


    }
}
