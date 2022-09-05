package com.suchenghe.study.example_leakDetection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
public class SchLeakDetectionClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchLeakDetectionClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf returnByteBuf = ctx.alloc().heapBuffer();
        returnByteBuf.writeBytes(String.valueOf(System.currentTimeMillis()).getBytes());

        ctx.writeAndFlush(returnByteBuf);
        TimeUnit.MILLISECONDS.sleep(1);

        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf returnByteBuf = ctx.alloc().heapBuffer();
        returnByteBuf.writeBytes("12345".getBytes());

        ctx.writeAndFlush(returnByteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
