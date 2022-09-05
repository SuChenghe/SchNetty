package com.suchenghe.study.example_ChannelGroup;

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
 * @date 2022/7/27 15:57
 */
public class SchServer_ChannelGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchServer_ChannelGroup.class);

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
                .childHandler(new SchInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(61005).sync();
            LOGGER.info("SchServer服务端启动成功,port:{},for ChannelGroup",61005);
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
