package com.suchenghe.example_exceptionCaught;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchExceptionCaughtInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new SchInBindHandlerA());
        channelPipeline.addLast(new SchInBindHandlerB());
        channelPipeline.addLast(new SchInBindHandlerC());
        channelPipeline.addLast(new SchOutBindHandlerA());
        channelPipeline.addLast(new SchOutBindHandlerB());
        channelPipeline.addLast(new SchOutBindHandlerC());
        channelPipeline.addLast(new SchExceptionControlHandler());
    }
}