//package com.suchenghe.study.back.client;
//
//import com.suchenghe.study.back.proto.SchClientProtobuf;
//import com.suchenghe.study.back.proto.SchServerProtobuf;
//import com.suchenghe.study.normal.handler.MyServerHandlerProcess;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author SuChenghe
// * @date 2018/7/27 16:18
// */
//public class SchClientHandler extends SimpleChannelInboundHandler {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerProcess.class);
//
//    /**
//     * 在被通知Channel是活跃的时候，发送一条消息
//     * @param channelHandlerContext
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext channelHandlerContext){
//        SchClientProtobuf.Request message = SchClientProtobuf.Request.newBuilder()
//                .setSop("5A").setCmd("active").setData("Channel is active").build();
//        channelHandlerContext.writeAndFlush(message);
//    }
//
//    /**
//     * 当从服务器接收到一条消息时被调用
//     * @param channelHandlerContext
//     * @param fromServer
//     * @throws Exception
//     */
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object fromServer) throws Exception {
//        SchServerProtobuf.Response response = (SchServerProtobuf.Response) fromServer;
//        System.out.println("client get msg from server:" + response);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
//        LOGGER.error("Client exception:"+cause.getMessage().toString());
//        channelHandlerContext.close();
//    }
//
//
//}
