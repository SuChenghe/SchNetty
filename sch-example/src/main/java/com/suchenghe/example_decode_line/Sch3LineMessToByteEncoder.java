package com.suchenghe.example_decode_line;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/8/3 13:50
 */
public class Sch3LineMessToByteEncoder extends MessageToByteEncoder<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sch3LineMessToByteEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        out.writeBytes((msg + "-3\r\n").getBytes());
    }

}
