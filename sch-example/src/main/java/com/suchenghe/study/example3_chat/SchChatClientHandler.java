package com.suchenghe.study.example3_chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
public class SchChatClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchChatClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
