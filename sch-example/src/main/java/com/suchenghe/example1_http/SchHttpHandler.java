package com.suchenghe.example1_http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author SuChenghe
 * @date 2020/9/5 20:21
 */
public class SchHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchHttpHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;

            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);

            URI uri = new URI(httpRequest.uri());
            LOGGER.info(httpRequest.method().name()+","+uri.getPath());

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelRegistered:"+ctx.channel().config().getOptions().toString());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelActive:"+ctx.channel().config().getOptions().toString());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelInactive:"+ctx.channel().config().getOptions().toString());
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelUnregistered:"+ctx.channel().config().getOptions().toString());
        super.channelUnregistered(ctx);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用
     * @param channelHandlerContext
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        LOGGER.error("Server exception:"+cause.getMessage());
//        cause.printStackTrace();
        //关闭该Channel
        //channelHandlerContext.close();
//        LOGGER.info("Channel Close");
    }
}
