//package com.suchenghe.study.example6_protobuf;
//
//import com.suchenghe.study.protobuf.SchFamilyProtobuf;
//import com.suchenghe.study.protobuf.SchProtobuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Random;
//
///**
// * @author SuChenghe
// * @date 2020/09/13 23:05
// */
//public class SchProtobufClientHandler extends SimpleChannelInboundHandler<SchFamilyProtobuf.SchMessage> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SchProtobufClientHandler.class);
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, SchFamilyProtobuf.SchMessage msg) throws Exception {
//
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        Random random = new Random();
//        int num = random.nextInt(3)+1;
//        SchFamilyProtobuf.SchMessage schMessage ;
//        if(num == 1){
//            schMessage = SchFamilyProtobuf.SchMessage.newBuilder()
//                    .setRoleType(SchFamilyProtobuf.SchMessage.role.myself)
//                    .setSch(SchFamilyProtobuf.Sch.newBuilder().setName("苏成贺").setAge("30").build())
//                    .build();
//        }else if(num == 2){
//            schMessage = SchFamilyProtobuf.SchMessage.newBuilder()
//                    .setRoleType(SchFamilyProtobuf.SchMessage.role.mywife)
//                    .setZjf(SchFamilyProtobuf.Zjf.newBuilder().setName("朱佳芳").setAge("26").build())
//                    .build();
//        }else {
//            schMessage = SchFamilyProtobuf.SchMessage.newBuilder()
//                    .setRoleType(SchFamilyProtobuf.SchMessage.role.myson)
//                    .setSjz(SchFamilyProtobuf.Sjz.newBuilder().setName("苏君哲").setAge("3").build())
//                    .build();
//        }
//        ctx.writeAndFlush(schMessage);
//
//
////        SchProtobuf.SchMessage schMessage = SchProtobuf.SchMessage.newBuilder()
////                .setName("苏成贺").setCode(20).setAddress("杭州").build();
////        ctx.writeAndFlush(schMessage);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//        ctx.close();
//    }
//}
