package com.suchenghe.study.example_leakDetection;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchLeakDetectionClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new SchLeakDetectionClientHandler());
    }
}
