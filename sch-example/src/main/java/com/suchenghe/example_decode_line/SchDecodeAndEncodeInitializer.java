package com.suchenghe.example_decode_line;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchDecodeAndEncodeInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new Sch1LineBasedFrameDecoder(5));
        channelPipeline.addLast(new Sch2LineByteToMessDecoder());
        channelPipeline.addLast(new Sch3LineMessToByteEncoder());
    }
}
