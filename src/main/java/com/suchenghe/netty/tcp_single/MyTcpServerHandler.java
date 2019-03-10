package com.suchenghe.netty.tcp_single;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */

public class MyTcpServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTcpServerHandler.class);
    private static final int MAX_FRAME_SIZE = 1024;
    private String deviceId;

    /**
     * 对每个传入的消息都用调用
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg)
            throws Exception {
        //读取数据流
        ByteBuf in = (ByteBuf) msg;
        int readable = in.readableBytes();
        if (readable > MAX_FRAME_SIZE) {
            in.skipBytes(readable);
            throw new TooLongFrameException("Frame too big");
        }
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);

        byte[] returnByte = null;

        //写入数据信息
        ByteBuf resp = Unpooled.copiedBuffer(returnByte);
        channelHandlerContext.write(resp);
        channelHandlerContext.flush();
        ReferenceCountUtil.release(in);
    }

    /**
     * 操作完成时通知
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        String errorInfo = cause.getMessage().toString();
        if (("Connection reset by peer").equals(errorInfo)) {
            //It is safe to ignore the 'Connection reset by peer'
        } else {
            LOGGER.error("Server exception:" + errorInfo);
        }
        //关闭该Channel
        channelHandlerContext.close();
        deviceId = null;
    }

}
