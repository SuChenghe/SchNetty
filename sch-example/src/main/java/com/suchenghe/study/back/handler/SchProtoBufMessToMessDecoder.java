//package com.suchenghe.study.back.handler;
//
//import com.suchenghe.study.back.proto.SchClientProtobuf;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufAllocator;
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
///**
// * @author SuChenghe
// * @date 2018/7/31 10:50
// */
//public class SchProtoBufMessToMessDecoder extends MessageToMessageDecoder {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SchProtoBufMessToMessDecoder.class);
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
//
//        LOGGER.info("receive data:"+msg);
//        SchClientProtobuf.Request request = (SchClientProtobuf.Request) msg;
//
//        //ChannelInboundHandler之间的传递，通过调用ctx.fireChannelRead(msg)
//        //调用ctx.write(msg) 将传递到ChannelOutboundHandler。
//
//        //protobuf
//        SchClientProtobuf.Request requestNext = SchClientProtobuf.Request.newBuilder()
//                .setSop(request.getSop()).setCmd(request.getCmd()).setData(request.getData()+"-next").build();
//
//        SchClientProtobuf.Request requestNext2 = SchClientProtobuf.Request.newBuilder()
//                .setSop(request.getSop()).setCmd(request.getCmd()).setData(request.getData()+"-next-next").build();
//
//
//        out.add(requestNext);
//
////        byteBufAllocatorTest(ctx);
////        pooledByteBufAllocatorTest(ctx,requestNext2);
//    }
//
//    private void byteBufAllocatorTest(ChannelHandlerContext ctx){
//        //内存池设置
//        //默认的设置为:DefaultChannelConfig-->
//        //-->private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
//        //-->ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;
//        //-->ByteBufUtil.DEFAULT_ALLOCATOR-->io.netty.allocator.type
//        //如何设置(默认为pool):
//        //1、VM参数配置:-Dio.netty.allocator.type=pooled  -Dio.netty.allocator.type=unpooled
//        //2、服务启动类中设置
//        //.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//        //.childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
//
//        //默认的堆外内存方式
//        //1、如何切换到堆内->io.netty.noPreferDirect = true;
//        //2、服务启动类
//        //UnpooledByteBufAllocator unpooledByteBufAllocator = new UnpooledByteBufAllocator(false);-->(boolean preferDirect)
//        //serverBootStrap.childOption(ChannelOption.ALLOCATOR, unpooledByteBufAllocator)
//
//        ByteBufAllocator byteBufAllocator = ctx.alloc();
//        ByteBufAllocator byteBufAllocator2 = ctx.channel().alloc();
//
//        ByteBuf byteBuf1 = byteBufAllocator.buffer(1024*1024);
//        ByteBuf byteBuf2 = byteBufAllocator.heapBuffer(1024*1024);
//        ByteBuf byteBuf3 = byteBufAllocator.directBuffer(1024*1024);
//        ByteBuf byteBuf4 = byteBufAllocator.compositeBuffer(1024*1024);
//    }
//
//    private void pooledByteBufAllocatorTest(ChannelHandlerContext ctx, SchClientProtobuf.Request requestNext){
//        PooledByteBufAllocator pooledByteBufAllocator = new PooledByteBufAllocator(false);
//        ByteBuf byteBuf = pooledByteBufAllocator.heapBuffer(requestNext.toByteArray().length);
//        byteBuf.writeBytes(requestNext.toByteArray());
//        ctx.writeAndFlush(byteBuf);
//
//    }
//
//}
