package com.suchenghe.study.example7_task;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchTaskInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("fixedLengthFrameDecoder",new FixedLengthFrameDecoder(100));
        channelPipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast(new SchTaskHandler());
    }
}
