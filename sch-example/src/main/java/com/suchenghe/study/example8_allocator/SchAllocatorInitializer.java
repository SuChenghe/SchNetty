package com.suchenghe.study.example8_allocator;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author SuChenghe
 * @date 2022/05/10 09:21
 */
public class SchAllocatorInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));
        channelPipeline.addLast(new SchAllocatorHandler());
    }
}
