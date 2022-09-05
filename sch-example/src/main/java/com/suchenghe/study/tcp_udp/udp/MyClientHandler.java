package com.suchenghe.study.tcp_udp.udp;

import com.suchenghe.study.example_decode_encode.handler.MyServerHandlerProcess;
import com.suchenghe.study.example_decode_encode.util.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SuChenghe
 * @date 2018/7/27 16:18
 */
public class MyClientHandler extends SimpleChannelInboundHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerProcess.class);

    private final ByteBuf firstMessage;

    public MyClientHandler() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long time = Long.parseLong(sdf.format(new Date()));
        byte[] req = ByteUtils.longToByteByHighToLow(time);
        firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    /**
     * 在被通知Channel是活跃的时候，发送一条消息
     * @param channelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("clientparam", CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一条消息时被调用
     * @param channelHandlerContext
     * @param fromServer
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object fromServer) throws Exception {
        ByteBuf responseBuf = (ByteBuf) fromServer;
        System.out.println("Client:接收到了:" + responseBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        LOGGER.error("Client exception:"+cause.getMessage().toString());
        channelHandlerContext.close();
    }


}
