package com.suchenghe.study.example_decode_encode.handler.decode_encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SuChenghe
 * @date 2018/8/3 13:50
 */
public class MyServerHandlerByteToMessDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyServerHandlerByteToMessDecoder.class);

    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if(readable > MAX_FRAME_SIZE){
            //跳过所有可读字节
            in.skipBytes(readable);
            //抛出TooLongFrameException并且通知ChannelHandler
            throw new TooLongFrameException("Frame too big");
        }

        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        Long num = byteTolong(req);
        System.out.println("ByteToMessDecoder"+num);
        out.add(num);

    }

    /**
     * 将字节转换为long类型(低字节在后，高字节在前)
     */
    private static long byteTolong(byte[] byteArray) {
        String[] hexArray = getHexString(byteArray);
        StringBuilder hexStringBuilder = new StringBuilder();
        int length = hexArray.length;
        for (int i = 0; i < length; i++) {
            hexStringBuilder.append(hexArray[i]);
        }
        return Long.parseLong(hexStringBuilder.toString(), 16);
    }

    /**
     * 获取16进制String数组
     */
    private static String[] getHexString(byte[] byteArray) {
        String[] hexArray = new String[byteArray.length];
        for (int i = 0; i < hexArray.length; i++) {
            String hex = Integer.toHexString(byteArray[i] & 0xFF);
            if (1 == hex.length()) {
                hexArray[i] = "0" + hex;
            } else {
                hexArray[i] = hex;
            }
        }
        return hexArray;
    }

}
