package com.suchenghe.netty.udp;

import com.suchenghe.netty.common.ByteUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static void main(String[] args) throws InterruptedException {

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyClient("127.0.0.1", 61005).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0L, 5L, TimeUnit.SECONDS);


    }

    public void start() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long time = Long.parseLong(sdf.format(new Date()));
        byte[] req = ByteUtils.longToByteByHighToLow(time);
        ByteBuf bytebuf = Unpooled.buffer(req.length);
        bytebuf.writeBytes(req);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new MyClientHandler());

            Channel ch = b.bind(0).sync().channel();
            // 向网段类所有机器广播发UDP
            ch.writeAndFlush(
                    new DatagramPacket(
                            bytebuf,
                            new InetSocketAddress(
                                    host, port
                            ))).sync();
            if (!ch.closeFuture().await(15000)) {
                System.out.println("查询超时！！！");
            }
        } finally {
            group.shutdownGracefully();
        }

    }
}
