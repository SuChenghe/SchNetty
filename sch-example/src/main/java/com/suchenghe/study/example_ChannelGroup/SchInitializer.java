package com.suchenghe.study.example_ChannelGroup;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author SuChenghe
 * @date 2022/07/27 16:28
 */
public class SchInitializer extends ChannelInitializer<SocketChannel> {

    private final static ChannelGroup recipients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("SchNoConnectIdleEventHandler",  new AllChannelGroupHandler(recipients));
        channelPipeline.addLast(new SchHandler(recipients));
    }
}
