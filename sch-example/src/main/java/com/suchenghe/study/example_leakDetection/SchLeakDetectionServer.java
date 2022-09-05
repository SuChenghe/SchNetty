package com.suchenghe.study.example_leakDetection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/7/27 15:57
 */
public class SchLeakDetectionServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchLeakDetectionServer.class);

    /**
     * 启动服务
     */
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("io.netty.leakDetection.level", ResourceLeakDetector.Level.PARANOID.name());

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .handler(new LoggingHandler(LogLevel.ERROR))
                .childHandler(new SchLeakDetectionInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(61005).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
