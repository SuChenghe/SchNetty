package com.suchenghe.example_IdleStateHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchNoConnectIdleEventHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchNoConnectIdleEventHandler.class);

    private static int numberStart = 0;
    private static int numberAll = 3;

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            if(((IdleStateEvent) evt).state() == IdleState.READER_IDLE){
                LOGGER.info("IdleStateEvent READER_IDLE , Client with IP {}",
                        ChannelUtils.remoteIP(ctx.channel()).orElse("UNKNOWN"));
            }else if(((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE){
                LOGGER.info("IdleStateEvent WRITER_IDLE , Client with IP {}",
                        ChannelUtils.remoteIP(ctx.channel()).orElse("UNKNOWN"));
            }else if(((IdleStateEvent) evt).state() == IdleState.ALL_IDLE){
                LOGGER.info("IdleStateEvent ALL_IDLE , Client with IP {}",
                        ChannelUtils.remoteIP(ctx.channel()).orElse("UNKNOWN"));
            }
            if(numberStart ++ > numberAll){
                ctx.close();
                LOGGER.info("Client with IP {} disconnected. The client was idle for too long without sending a Message",
                        ChannelUtils.remoteIP(ctx.channel()).orElse("UNKNOWN"));
            }
            return;
        }
        super.userEventTriggered(ctx, evt);
    }
}
