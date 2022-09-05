package com.suchenghe.study.unit_test.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

/**
 * @author SuChenghe
 * @date 2020/3/29 19:44
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(100);
        for(int i = 0 ;i <= 10 ;i++){
            byteBuf.writeByte(i);
        }
        for(int i = 1 ;i <= 3 ;i++){
            System.out.println("get:"+byteBuf.getByte(3));
            System.out.println("read:"+byteBuf.readByte());
        }
        System.out.println("readerIndex:"+byteBuf.readerIndex(1).readByte());
        System.out.println("read:"+byteBuf.readByte());

        byteBuf.setIndex(1,byteBuf.writerIndex());
        System.out.println("setIndex:"+byteBuf.readByte());

        byteBuf.discardReadBytes();
        System.out.println("discardReadBytes:"+byteBuf.getByte(0));

        byteBuf.markReaderIndex();
        byteBuf.setIndex(1,byteBuf.writerIndex());
        System.out.println("markReaderIndex:"+byteBuf.readByte());
        byteBuf.resetReaderIndex();
        System.out.println("resetReaderIndex:"+byteBuf.readByte());

        System.out.println("indexOf:"+byteBuf.indexOf(0,10, (byte) 7));
        System.out.println("bytesBefore:"+byteBuf.bytesBefore((byte) 7));
        byteBuf.writeByte((byte) '\n');
        System.out.println("forEachByte:"+byteBuf.forEachByte(ByteProcessor.FIND_LF));


    }

}
