package com.suchenghe.netty.udp;

import com.suchenghe.netty.common.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器
 *
 * @author SuChenghe
 * @date 2018/8/10 16:18
 */
@Component
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UdpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        long time = ByteUtils.byteTolongByHighToLow(req);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = sdf.parse(String.valueOf(time));
        SimpleDateFormat printsdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(printsdf.format(date));
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
        //关闭该Channel
        //channelHandlerContext.close();
    }

}
