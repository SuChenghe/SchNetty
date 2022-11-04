package com.suchenghe.example_decode_line;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
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
public class SchDecodeAndEncodeServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchDecodeAndEncodeServer.class);

    /**
     * 启动服务
     */
    public static void main(String[] args) throws InterruptedException {

        //System.setProperty("io.netty.allocator.type","unpooled");

        int parentThreads = 1;
        int childThreads = 1;

        LOGGER.debug("NioEventLoopGroup 开始实例化: new NioEventLoopGroup("+parentThreads+",new DefaultThreadFactory(\"schParentGroup\"));");
        EventLoopGroup parentGroup = new NioEventLoopGroup(parentThreads,new DefaultThreadFactory("schParentGroup"));
        EventLoopGroup childGroup = new NioEventLoopGroup(childThreads,new DefaultThreadFactory("schChildGroup"));
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup,childGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new SchDecodeAndEncodeInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(61005).sync();
            LOGGER.info("SchSimpleServer服务端启动成功,port:{}",61005);
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
