package com.suchenghe.study.example3_chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * @author SuChenghe
 * @date 2018/7/27 17:00
 */
@AllArgsConstructor
public class SchChatClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 61005))
                    .handler(new SchChatClientInitializer());

            Channel channel = bootstrap.connect().sync().channel();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
            }
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
