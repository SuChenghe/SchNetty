//package com.suchenghe.study.example6_protobuf;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import lombok.AllArgsConstructor;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//
///**
// * @author SuChenghe
// * @date 2018/7/27 17:00
// */
//@AllArgsConstructor
//public class SchProtobufClient {
//
//    public static void main(String[] args) throws InterruptedException, IOException {
//        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
//                    .remoteAddress(new InetSocketAddress("127.0.0.1", 61005))
//                    .handler(new SchProtobufClientInitializer());
//
//            ChannelFuture channelFuture = bootstrap.connect().sync();
//            channelFuture.channel().closeFuture().sync();
//
//        } finally {
//            eventLoopGroup.shutdownGracefully().sync();
//        }
//    }
//}
