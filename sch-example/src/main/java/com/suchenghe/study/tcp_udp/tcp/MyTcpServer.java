package com.suchenghe.study.tcp_udp.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * TCP-服务端启动
 *
 * @author SuChenghe
 * @date 2018/12/29 12:35
 */
//@Service
public class MyTcpServer implements ApplicationListener<ApplicationReadyEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyTcpServer.class);

  ExecutorService executorService = Executors.newSingleThreadExecutor();

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        //配置服务端的NIO线程组
        //用于服务端接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于SocketChannel的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
          //创建ServerBootstrap
          ServerBootstrap serverBootstrap = new ServerBootstrap();
          serverBootstrap.group(bossGroup, workerGroup)
              //指定所使用的NIO传输Channel
              .channel(NioServerSocketChannel.class)
              //使用指定的端口,使用这个地址监听新的连接请求,
              //port要加入到Disconf配置中,因为配置文件暂未统一,先写死
              .localAddress(new InetSocketAddress(10001))
              .option(ChannelOption.SO_BACKLOG, 1024)
              //当一个新的连接被接收时，一个新的子Channel将会被创建，而ChannelInitializer将会myServerHandler实例添加到该Channel的ChannelPipeline中
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline().addLast(new MyTcpServerHandler());
                }
              });
          try {
            //异步绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            LOGGER.info("TCP,Netty服务端启动成功,端口号:10001");
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
    });
  }

}
