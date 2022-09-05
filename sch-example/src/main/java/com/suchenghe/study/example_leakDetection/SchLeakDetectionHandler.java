package com.suchenghe.study.example_leakDetection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchLeakDetectionHandler extends ChannelInboundHandlerAdapter {

    private static long num = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        ReferenceCountUtil.release(msg);

        ByteBuf returnByteBuf = ctx.alloc().buffer();
        returnByteBuf.writeBytes(String.valueOf(System.currentTimeMillis()).getBytes());

        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator(false);

//      System.out.println(ByteBufUtil.hexDump(returnByteBuf));

        ctx.writeAndFlush(returnByteBuf);

        num++;
        if(num % 1000 == 0){
            System.out.println(num);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
