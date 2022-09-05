//package com.suchenghe.study.example6_protobuf;
//
//import com.suchenghe.study.protobuf.SchFamilyProtobuf;
//import com.suchenghe.study.protobuf.SchProtobuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//
///**
// * @author SuChenghe
// * @date 2020/9/5 20:21
// */
//public class SchProtobufServerHandler extends SimpleChannelInboundHandler<SchFamilyProtobuf.SchMessage> {
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, SchFamilyProtobuf.SchMessage msg) throws Exception {
//        if(msg.getRoleType() == SchFamilyProtobuf.SchMessage.role.myself){
//            System.out.println(msg.getSch().getName()+","+msg.getSch().getAge());
//        }else if(msg.getRoleType() == SchFamilyProtobuf.SchMessage.role.mywife){
//            System.out.println(msg.getZjf().getName()+","+msg.getZjf().getAge());
//        }else {
//            System.out.println(msg.getSjz().getName()+","+msg.getSjz().getAge());
//        }
//
//    }
//
//}
