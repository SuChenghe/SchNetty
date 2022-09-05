package com.suchenghe.study.example_decode_encode.handler.decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/8/3 13:50
 */
public class MyServerHandlerMessToByteEncoder extends MessageToByteEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerMessToByteEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        Integer ret = (Integer) msg;
        System.out.println("MessToByteEncoder:"+msg);
        ByteBuf resBuf = Unpooled.copiedBuffer(longToByteByHighToLow(ret));
        out.writeBytes(resBuf);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    /**
     * 将int类型转换为字节数组(高字节在前，低字节在后)
     */
    public static byte[] longToByteByHighToLow(int number) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[3 - i] = (byte) (number & 0xff);
            number >>= 8;
        }
        return b;
    }

}
