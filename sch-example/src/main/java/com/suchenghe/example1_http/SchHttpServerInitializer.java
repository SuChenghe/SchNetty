package com.suchenghe.example1_http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SuChenghe
 * @date 2020/9/5 16:28
 */
public class SchHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchHttpServerInitializer.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        channelPipeline.addLast("schHttpServerCodec",new HttpServerCodec());
        LOGGER.info("Add Handler : schHttpServerCodec");

        channelPipeline.addLast("schHttpHandler",new SchHttpHandler());
        LOGGER.info("Add Handler : schHttpHandler");

    }
}
