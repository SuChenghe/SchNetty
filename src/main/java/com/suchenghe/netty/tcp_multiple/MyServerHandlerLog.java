package com.suchenghe.netty.tcp_multiple;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
@ChannelHandler.Sharable
public class MyServerHandlerLog extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerLog.class);

    /**
     * 对每个传入的消息都用调用
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        LOGGER.debug("日志记录Handler......");
        System.out.println("日志记录Handler......");

        ReferenceCountUtil.release(msg);

    }

    /**
     * 操作完成时通知
     * channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)
     * .addListener(ChannelFutureListener.CLOSE);
     *
     * @param channelHandlerContext
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 在读取操作期间，有异常抛出时会调用
     *
     * @param channelHandlerContext
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        LOGGER.error("Server exception:" + cause.getMessage().toString());
        System.out.println("Server exception:" + cause.getMessage().toString());
        //关闭该Channel
        channelHandlerContext.close();
    }

}
