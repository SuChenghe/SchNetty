//package com.suchenghe.study.back.handler;
//
//import com.suchenghe.study.back.proto.SchServerProtobuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageEncoder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
///**
// * @author SuChenghe
// * @date 2018/7/31 10:50
// */
//public class SchProtoBufMessToMessEncoder extends MessageToMessageEncoder {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SchProtoBufMessToMessEncoder.class );
//
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
//
//        LOGGER.info("send data:"+msg);
//
//        SchServerProtobuf.Response response = (SchServerProtobuf.Response) msg;
//
//        SchServerProtobuf.Response responseNext = SchServerProtobuf.Response.newBuilder()
//                .setSop("5A").setCmd(response.getCmd()).setCode(response.getCode())
//                .setMessage(response.getMessage()+"_next").build();
//
//        out.add(responseNext);
//
//    }
//}
