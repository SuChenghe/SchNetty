package com.suchenghe.example_outbind;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchOutBindHandlerB extends ChannelOutboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchOutBindHandlerB.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LOGGER.warn("B write " + msg);
        ctx.write(msg, promise);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                ctx.write("B say hello world1");
                ctx.pipeline().write("B say hello world2");
            }
        },3, TimeUnit.SECONDS);
    }

}
