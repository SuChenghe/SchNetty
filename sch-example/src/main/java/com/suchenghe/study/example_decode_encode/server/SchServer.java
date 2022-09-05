package com.suchenghe.study.example_decode_encode.server;

import com.suchenghe.study.example_decode_encode.handler.MyServerHandlerProcess;
import com.suchenghe.study.example_decode_encode.handler.decode_encode.MyServerHandlerByteToMessDecoder;
import com.suchenghe.study.example_decode_encode.handler.decode_encode.MyServerHandlerMessToByteEncoder;
import com.suchenghe.study.example_decode_encode.handler.decode_encode.MyServerHandlerMessToMessDecoder;
import com.suchenghe.study.example_decode_encode.handler.decode_encode.MyServerHandlerMessToMessEncoder;
import com.suchenghe.study.example_decode_encode.handler.log.MyServerHandlerLog;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @author SuChenghe
 * @date 2018/7/27 15:57
 */

public class SchServer {

    public static void main(String[] args) {
        final MyServerHandlerLog myServerHandlerLog = new MyServerHandlerLog();
        final MyServerHandlerProcess myServerHandlerProcess = new MyServerHandlerProcess();

        //配置服务端的NIO线程组
        //用于服务端接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("schBossGroup"));
        //用于SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("schWorkGroup"));
        try {
            //创建ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    //指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口,使用这个地址监听新的连接请求
                    .localAddress(new InetSocketAddress(61005))
                    //ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，
                    //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //当一个新的连接被接收时，一个新的子Channel将会被创建，而ChannelInitializer将会myServerHandler实例添加到该Channel的ChannelPipeline中
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            MyServerHandlerByteToMessDecoder myServerHandlerByteToMessDecoder = new MyServerHandlerByteToMessDecoder();
                            MyServerHandlerMessToMessDecoder myServerHandlerMessToMessDecoder = new MyServerHandlerMessToMessDecoder();
                            MyServerHandlerMessToMessEncoder myServerHandlerMessToMessEncoder = new MyServerHandlerMessToMessEncoder();
                            MyServerHandlerMessToByteEncoder myServerHandlerMessToByteEncoder = new MyServerHandlerMessToByteEncoder();
                            socketChannel.pipeline()
                                    .addLast("schEncodeMessToByte",myServerHandlerMessToByteEncoder)
                                    .addLast("schEncodeMessToMess",myServerHandlerMessToMessEncoder)
                                    //设置日志级别
                                    .addLast("schLoggerHandler",new LoggingHandler(LogLevel.WARN))
                                    .addLast("schDecodeByteToMess",myServerHandlerByteToMessDecoder)
                                    .addLast("schDecodeMessToMess",myServerHandlerMessToMessDecoder)
                                    .addLast(myServerHandlerProcess);
                        }
                    });
            try {
                //异步绑定服务器，调用sync()方法阻塞等待直到绑定完成
                ChannelFuture channelFuture = serverBootstrap.bind().sync();
                //获取Channel的closeFuture，并且阻塞当前线程直到它完成
                channelFuture.channel().closeFuture().sync();
                System.out.println("-----------------------------------------");
            } catch (InterruptedException e) {
            }

        } finally {
            //退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
