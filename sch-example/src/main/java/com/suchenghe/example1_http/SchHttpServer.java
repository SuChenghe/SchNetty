package com.suchenghe.example1_http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:22
 */
public class SchHttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchHttpServer.class);

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new SchHttpServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            LOGGER.info("SchHttpServer服务端启动成功");
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }



}
