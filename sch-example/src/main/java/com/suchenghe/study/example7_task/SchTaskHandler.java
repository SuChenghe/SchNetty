package com.suchenghe.study.example7_task;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchTaskHandler extends SimpleChannelInboundHandler<String>{

    private static final Logger LOGGER = LoggerFactory.getLogger(SchTaskHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        LOGGER.info(ctx.channel().remoteAddress()+","+msg);

        ctx.writeAndFlush("server return for "+ msg);

        LOGGER.info("ctx.executor().schedule start");
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Schedule Job Out : 5 Seconds");
                ctx.writeAndFlush("server ctx.executor().schedule return for "+ msg);
            }
        },5, TimeUnit.SECONDS);
        LOGGER.info("ctx.executor().schedule end");

        LOGGER.info("ctx.executor().execute start");
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush("server ctx.executor return for "+ msg);
                LOGGER.info("Execute Job Out");
            }
        });
        LOGGER.info("ctx.executor().execute end");LOGGER.info("ctx.executor().schedule start");
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Schedule Job Out : 5 Seconds");
                ctx.writeAndFlush("server ctx.executor().schedule return for "+ msg);
            }
        },5, TimeUnit.SECONDS);
        LOGGER.info("ctx.executor().schedule end");

        LOGGER.info("ctx.executor().execute start");
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush("server ctx.executor return for "+ msg);
                LOGGER.info("Execute Job Out");
            }
        });
        LOGGER.info("ctx.executor().execute end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
