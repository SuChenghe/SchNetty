package com.suchenghe.netty.tcp_multiple;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Sharable：标示一个Channel-Handler可以被多个Channel安全地共享
 *
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
@ChannelHandler.Sharable
public class MyServerHandlerProcess extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerProcess.class);

    /**
     * 对每个传入的消息都用调用
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        System.out.println("ServerHandlerProcess:" + msg);

        //ChannelInboundHandler之间的传递，通过调用ctx.fireChannelRead(msg)
        //调用ctx.write(msg) 将传递到ChannelOutboundHandler。

        //ByteBuf resp = Unpooled.copiedBuffer("567".getBytes());

        channelHandlerContext.write(5);

//        channelHandlerContext.flush();


//        ReferenceCountUtil.release(in);

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
//        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        channelFuture.addListener(ChannelFutureListener.CLOSE);
        LOGGER.debug("ServerHandlerProcess:读操作完成");
        System.out.println("ServerHandlerProcess:读操作完成");
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
