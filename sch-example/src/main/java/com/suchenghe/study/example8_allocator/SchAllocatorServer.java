package com.suchenghe.study.example8_allocator;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2022/05/10 09:21
 */
public class SchAllocatorServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchAllocatorServer.class);

    /**
     * 启动服务
     */
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup(new DefaultThreadFactory("schParentGroup"));
        EventLoopGroup childGroup = new NioEventLoopGroup(new DefaultThreadFactory("schChildGroup"));
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup,childGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new SchAllocatorInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(61005).sync();
            LOGGER.info("SchAllocatorServer服务端启动成功,port:{}",61005);
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
