package com.suchenghe.study.example3_chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast("lengthFieldBasedFrameDecoder",
                new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        channelPipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));

        channelPipeline.addLast(new SchChatServerHandler());
    }
}
