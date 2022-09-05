package com.suchenghe.study.example2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
public class SchStringClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchStringClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.writeAndFlush("from client:Query Time");
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("Hello Server");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
