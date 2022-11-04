package com.suchenghe.example_decode_line;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SuChenghe
 * @date 2022/11/3 20:57
 */
public class Sch2LineByteToMessDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sch2LineByteToMessDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] valueByte = new byte[in.readableBytes()];
        in.readBytes(valueByte);
        String value = new String(valueByte);
        ctx.pipeline().writeAndFlush(value + "-2");
    }

}
