package com.suchenghe.study.example_AttributeKey;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.time.LocalDateTime;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchServerAttributeHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().attr(AttributeKey.valueOf("schAttribute1")).get());
        System.out.println(ctx.channel().attr(AttributeKey.valueOf("schAttribute2")).get());
        System.out.println(ctx.channel().attr(AttributeKey.valueOf("schAttribute3")).get());
        System.out.println(ctx.channel().attr(AttributeKey.valueOf("schAttribute4")).get());
        System.out.println(ctx.channel().attr(AttributeKey.valueOf("schAttribute5")).get());
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.writeAndFlush("from server:" + LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
