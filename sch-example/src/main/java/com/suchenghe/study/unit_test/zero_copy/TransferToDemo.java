package com.suchenghe.study.unit_test.zero_copy;

import lombok.SneakyThrows;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author SuChenghe
 * @date 2019/1/27 18:27
 */
public class TransferToDemo {

  @SneakyThrows
  public static void main(String[] args) {
    RandomAccessFile file = new RandomAccessFile("nio-data.txt", "rw");

    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1000);
    for (int i = 0; i < 100; i++) {
      byteBuffer.put(i, (byte) i);
    }

    FileChannel inChannel = file.getChannel();
    inChannel.write(byteBuffer);

    RandomAccessFile outFile = new RandomAccessFile("nio-data-out.txt", "rw");
    FileChannel outChannel = outFile.getChannel();

    long transferred = inChannel.transferTo(0, inChannel.size(), outChannel);
    System.out.println(transferred);

  }
}