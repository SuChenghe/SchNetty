//package com.suchenghe.study.back.handler;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.CharsetUtil;
//
///**
// * @author SuChenghe
// * @date 2020/4/3 0:05
// */
//public class SchHeartBeatHandler extends ChannelInboundHandlerAdapter {
//
//    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
//                    "HEARTBEAT", CharsetUtil.ISO_8859_1));
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx,
//                                   Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            //发送心跳消息，并在发送失败时关闭该连接
//            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
//                    ChannelFutureListener.CLOSE_ON_FAILURE);
//        } else {
//            //不是 IdleStateEvent事件，将它传递给下一个ChannelInboundHandler
//            super.userEventTriggered(ctx, evt);
//        }
//    }
//
//}
