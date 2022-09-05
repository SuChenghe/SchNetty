package com.suchenghe.study.example_decode_encode.handler.decode_encode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SuChenghe
 * @date 2018/7/31 10:50
 */
public class MyServerHandlerMessToMessDecoder extends MessageToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerMessToMessDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

        msg = msg+"2";
        System.out.println("MessToMessDecoder"+msg);

        out.add(msg);

    }

}
