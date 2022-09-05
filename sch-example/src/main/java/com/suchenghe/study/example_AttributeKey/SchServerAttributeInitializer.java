package com.suchenghe.study.example_AttributeKey;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchServerAttributeInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("ch:"+ch);

        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast("stringDecoder",new StringDecoder(CharsetUtil.UTF_8));
        channelPipeline.addLast("stringEncoder",new StringEncoder(CharsetUtil.UTF_8));

        channelPipeline.addLast(new SchServerAttributeHandler());

        //ch.attr(AttributeKey.newInstance("schAttribute1")).set("schAttribute1value");
        ch.attr(AttributeKey.valueOf("schAttribute1")).set("schAttribute11value");

        ch.attr(AttributeKey.valueOf("schAttribute2")).set("schAttribute2value");
        //ch.attr(AttributeKey.newInstance("schAttribute2")).set("schAttribute22value");
        //java.lang.IllegalArgumentException: 'schAttribute2' is already in use

        ch.attr(AttributeKey.valueOf("schAttribute3")).set("schAttribute3value");
        ch.attr(AttributeKey.valueOf("schAttribute4")).set("schAttribute4value");
        ch.attr(AttributeKey.valueOf("schAttribute5"+System.currentTimeMillis())).set("schAttribute5value");

    }
}
