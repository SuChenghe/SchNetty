package com.suchenghe.study.example8_allocator;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2022/05/10 09:21
 */
public class SchAllocatorHandler extends SimpleChannelInboundHandler<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SchAllocatorHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LOGGER.info(ctx.channel().remoteAddress()+","+msg);

        ctx.writeAndFlush("server return for "+ msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
