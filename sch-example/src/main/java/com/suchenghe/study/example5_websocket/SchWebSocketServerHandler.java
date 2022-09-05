package com.suchenghe.study.example5_websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("接收到消息：" + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText()+",加入\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText()+",离开\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
