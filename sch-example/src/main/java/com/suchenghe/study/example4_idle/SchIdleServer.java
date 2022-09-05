package com.suchenghe.study.example4_idle;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/7/27 15:57
 */
public class SchIdleServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchIdleServer.class);

    /**
     * 启动服务
     */
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("schBossGroup"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("schWorkGroup"));
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new SchIdleServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(61005).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
