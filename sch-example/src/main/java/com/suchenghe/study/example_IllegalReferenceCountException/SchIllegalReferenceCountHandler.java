package com.suchenghe.study.example_IllegalReferenceCountException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchIllegalReferenceCountHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private HashMap<Long, ByteBuf> hashMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(msg.toString());
        ctx.writeAndFlush("from server:" + LocalDateTime.now());

        hashMap.put(System.currentTimeMillis(),msg);

        if(hashMap.size() > 1){
            hashMap.forEach((aLong, byteBuf) -> {
                //io.netty.util.IllegalReferenceCountException: refCnt: 0
                System.out.println(byteBuf.getByte(1));
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
