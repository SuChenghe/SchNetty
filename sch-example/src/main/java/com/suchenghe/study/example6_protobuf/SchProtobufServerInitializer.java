//package com.suchenghe.study.example6_protobuf;
//
//import com.suchenghe.study.protobuf.SchFamilyProtobuf;
//import com.suchenghe.study.protobuf.SchProtobuf;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.protobuf.ProtobufDecoder;
//import io.netty.handler.codec.protobuf.ProtobufEncoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
//
///**
// * @author SuChenghe
// * @date 2020/9/12 11:00
// */
//public class SchProtobufServerInitializer extends ChannelInitializer<SocketChannel> {
//
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline channelPipeline = ch.pipeline();
//
//        channelPipeline.addLast(new ProtobufVarint32FrameDecoder());
//
//        channelPipeline.addLast(new ProtobufDecoder(null));
//        channelPipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//        channelPipeline.addLast(new ProtobufEncoder());
//
//        channelPipeline.addLast(new SchProtobufServerHandler());
//    }
//}
