package com.suchenghe.study.simple.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sharable：标示一个Channel-Handler可以被多个Channel安全地共享
 *
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
public class SimpleProcess extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleProcess.class);

    /**
     * 对每个传入的消息都用调用
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
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
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.flush();
//        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        channelFuture.addListener(ChannelFutureListener.CLOSE);
        LOGGER.info("ServerHandlerProcess:读操作完成");
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("11111111111111");
    }

    /**
     * 在读取操作期间，有异常抛出时会调用
     * @param channelHandlerContext
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        LOGGER.error("Server exception:"+cause.getMessage());
        //关闭该Channel
        channelHandlerContext.close();
    }

}
