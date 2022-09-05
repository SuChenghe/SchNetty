package com.suchenghe.study.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author SuChenghe
 * @date 2018/10/28 11:20
 */
public class TimeClientHandler {

  /**
   * 向服务器写消息
   * @param socketChannel
   * @throws IOException
   */
  public void doWrite(SocketChannel socketChannel) throws IOException {
    byte[] bytes = "告诉我时间".getBytes();
    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
    writeBuffer.put(bytes);
    writeBuffer.flip();
    socketChannel.write(writeBuffer);
    if(!writeBuffer.hasRemaining()){
      System.out.println("Send order to server success");
    }
  }

  public void handelInput(Selector selector, SelectionKey selectionKey) throws IOException{
    if(selectionKey.isValid()){
      //判断连接是否完成
      SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
      if(selectionKey.isConnectable()){
        if(socketChannel.finishConnect()){
          socketChannel.register(selector,SelectionKey.OP_READ);
          doWrite(socketChannel);
        }else{
          //连接失败，进程退出
          System.exit(1);
        }
      }
      if(selectionKey.isReadable()){
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        int readBytes = socketChannel.read(readByteBuffer);
        if(readBytes>0){
          //将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作。
          readByteBuffer.flip();
          byte[] bytes = new byte[readByteBuffer.remaining()];
          readByteBuffer.get(bytes);
          String body = new String(bytes,"UTF-8");
          System.out.println("客户端接收到消息:"+body);
        }else if(readBytes<0){
          //对端链路关闭
          selectionKey.channel();
          socketChannel.close();
        }else{
          //读到0字节,忽略
        }
      }
    }
  }

}
