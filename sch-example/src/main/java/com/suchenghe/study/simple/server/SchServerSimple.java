package com.suchenghe.study.simple.server;

import com.suchenghe.study.simple.handler.SimpleMessToMessDecoder;
import com.suchenghe.study.simple.handler.SimpleMessToMessEncoder;
import com.suchenghe.study.simple.handler.SimpleProcess;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 15:57
 */
public class SchServerSimple {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchServerSimple.class);

    /**
     * 启动服务
     */
    public static void main(String[] args) {
        //配置服务端的NIO线程组
        //用于服务端接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("schBossGroup"));
        //用于SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("schWorkGroup"));
        try {
            //创建ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(61005))
                    //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                    .option(ChannelOption.SO_BACKLOG,1024)

                    //SO_KEEPALIVE设置
//                        .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(NioChannelOption.of(StandardSocketOptions.SO_KEEPALIVE),true)

                    //当一个新的连接被接收时，一个新的子Channel将会被创建，而ChannelInitializer将会myServerHandler实例添加到该Channel的ChannelPipeline中
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()

                                    .addLast("schSimpleEncodeMessToByte",new SimpleMessToMessEncoder())
                                    .addLast("schDecodeByteToMess",new SimpleMessToMessDecoder())

                                    .addLast("schProcess",new SimpleProcess())

                                    //idle监测
                                    //.addLast("heartBeatHandler", new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS))
                                    .addLast("heartBeatHandler", new ReadTimeoutHandler(10,  TimeUnit.SECONDS))

                                    //设置日志级别
                                    .addLast("schLoggerHandler",new LoggingHandler(LogLevel.INFO))
                                    //对flush增强，减少flush次数牺牲延迟增强吞吐量
                                    .addLast("flushConsolidationHandler",
                                            new FlushConsolidationHandler(5,true));
                        }
                    });
            try {
                //异步绑定服务器，调用sync()方法阻塞等待直到绑定完成
                ChannelFuture channelFuture = serverBootstrap.bind().sync();
                LOGGER.info("Netty服务端启动成功");
                //获取Channel的closeFuture，并且阻塞当前线程直到它完成
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
            }

        } finally {
            //退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
