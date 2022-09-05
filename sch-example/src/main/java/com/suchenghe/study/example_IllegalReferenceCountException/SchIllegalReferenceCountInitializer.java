package com.suchenghe.study.example_IllegalReferenceCountException;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchIllegalReferenceCountInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast(new SchIllegalReferenceCountHandler());
    }
}
