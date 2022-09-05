package com.suchenghe.example_IdleStateHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2022/07/27 20:21
 */
public class SchIdleStateHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchIdleStateHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LOGGER.info(msg.toString());

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
