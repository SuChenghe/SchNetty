package com.suchenghe.study.example_simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchSimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(msg.toString());

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(msg);

        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
