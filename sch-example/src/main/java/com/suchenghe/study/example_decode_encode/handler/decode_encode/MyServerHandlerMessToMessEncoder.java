package com.suchenghe.study.example_decode_encode.handler.decode_encode;

import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SuChenghe
 * @date 2018/7/31 10:50
 */
public class MyServerHandlerMessToMessEncoder extends MessageToMessageEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerMessToMessEncoder.class );

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        System.out.println("MessToMessEncoder:"+msg);

        out.add(6);

    }
}
