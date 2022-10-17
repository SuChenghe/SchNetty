package com.suchenghe.example_exceptionCaught;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchInBindHandlerB extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchInBindHandlerB.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        throw new Exception("This is from SchInBindHandlerB Exception");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("SchInBindHandlerB exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

}
