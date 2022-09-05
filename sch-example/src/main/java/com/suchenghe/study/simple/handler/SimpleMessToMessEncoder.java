package com.suchenghe.study.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2020/04/03 11:01
 */
public class SimpleMessToMessEncoder extends MessageToByteEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMessToMessEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ByteBuf resp = Unpooled.copyLong(System.currentTimeMillis());
        ctx.write(resp);
//        ctx.writeAndFlush(resp);
    }
}
