package com.suchenghe.study.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SuChenghe
 * @date 2018/10/28 11:20
 */
public class TimeServerHandler {

  public void handelInput(Selector selector,SelectionKey selectionKey)  throws IOException{
    if(selectionKey.isValid()){
      //处理新接入的请求消息
      if(selectionKey.isAcceptable()){
        //Accept the new connection
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel  socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_READ);
      }
      if(selectionKey.isReadable()){
        //Read the data
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        int readBytes = socketChannel.read(readByteBuffer);
        if(readBytes>0){
          //将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作。
          readByteBuffer.flip();
          byte[] bytes = new byte[readByteBuffer.remaining()];
          readByteBuffer.get(bytes);
          String body = new String(bytes,"UTF-8");
          System.out.println("服务端接收到消息:"+body);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String result = "时间是:"+sdf.format(new Date());
          System.out.println(result);
          doWrite(socketChannel, result);
        }else if(readBytes<0){
          selectionKey.channel();
          socketChannel.close();
//          selector.keys().remove(selectionKey);
          System.out.println("客户端断开连接");
        }else{
          //忽略
        }
      }
    }
  }

  /**
   * 将消息发送给客户端
   * @param socketChannel
   * @param result
   * @throws IOException
   */
  private void doWrite(SocketChannel socketChannel,String result) throws IOException {
    if(!StringUtils.isEmpty(result)){
      byte[] bytes = result.getBytes("GB2312");
      ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
      writeBuffer.put(bytes);
      writeBuffer.flip();
      socketChannel.write(writeBuffer);
    }
  }
}
