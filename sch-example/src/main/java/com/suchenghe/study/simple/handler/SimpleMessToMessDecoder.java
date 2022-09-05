package com.suchenghe.study.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SuChenghe
 * @date 2018/8/3 13:50
 */
public class SimpleMessToMessDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMessToMessDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        String param = new String(req);
        LOGGER.info("client param:"+param);
        out.add(param);

        //真实副本
//        ByteBuf byteBuf = ctx.alloc().buffer();
//        byteBuf.writeInt(num);
//        out.add(byteBuf);
    }

}
