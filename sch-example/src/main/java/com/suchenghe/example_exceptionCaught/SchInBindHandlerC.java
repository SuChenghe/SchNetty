package com.suchenghe.example_exceptionCaught;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchInBindHandlerC extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchInBindHandlerC.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("SchInBindHandlerC exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }

}
