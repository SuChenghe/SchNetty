package com.suchenghe.example_IdleStateHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2022/07/27 16:28
 */
public class SchInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new SchIdleStateHandler());
        IdleStateHandler newConnectionIdleHandler = new IdleStateHandler(10000L, 0L, 0L, TimeUnit.MILLISECONDS);
        channelPipeline.addLast("SchIdleStateHandler", newConnectionIdleHandler);
        channelPipeline.addLast("SchNoConnectIdleEventHandler", new SchNoConnectIdleEventHandler());
    }
}
