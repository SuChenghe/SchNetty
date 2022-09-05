package com.suchenghe.study.example3_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        channelGroup.forEach(channel -> {
            if(ctx.channel() != channel){
                channel.writeAndFlush(ctx.channel().remoteAddress()+"说,"+ msg + "\n");
            }else{
                channel.writeAndFlush("自己,说:" + msg + "\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(channel.remoteAddress()+",加入\n");
        System.out.println(channel.remoteAddress()+",加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush(ctx.channel().remoteAddress()+",离开\n");
        System.out.println(ctx.channel().remoteAddress()+",离开\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+",已上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+",已下线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
