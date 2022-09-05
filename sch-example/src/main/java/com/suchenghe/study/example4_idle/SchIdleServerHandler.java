package com.suchenghe.study.example4_idle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchIdleServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            if(idleStateEvent.state() == IdleState.READER_IDLE){
                eventType = "读超时";
            }else if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                eventType = "写超时";
            }else if(idleStateEvent.state() == IdleState.ALL_IDLE){
                eventType = "读写超时";
            }
            System.out.println("超时事件:"+eventType);
        }
    }
}
