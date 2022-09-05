package com.suchenghe.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author SuChenghe
 * @date 2018/10/28 15:16
 */
public class TimeClient implements Runnable{

  private String host;
  private int port;
  /**
   * 多路复用器
   */
  private Selector selector;
  /**
   * 通道
   */
  private SocketChannel socketChannel;
  private volatile boolean stop;

  private TimeClientHandler timeClientHandler = new TimeClientHandler();

  @Override
  public void run() {
    try {
      doConnect();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    while (!stop) {
      try {
        selector.select(1500);
        Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
        SelectionKey selectionKey = null;
        while (selectionKeyIterator.hasNext()) {
          selectionKey = selectionKeyIterator.next();
          selectionKeyIterator.remove();
          try {
            timeClientHandler.handelInput(selector,selectionKey);
          } catch (Exception e) {
            if(null != selectionKey) {
              selectionKey.cancel();
              if (null != selectionKey.channel()) {
                selectionKey.channel().close();
              }
            }
          }
        }
      }catch (Exception e){
        System.exit(1);
      }
    }
    //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源，都会被自动去注册并关闭，所以不需要重复释放资源。
    if (null != selector) {
      try {
        selector.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public TimeClient(String host,int port) {
    if(null == host){
      this.host = "127.0.0.1";
    }else{
      this.host = host;
    }
    this.port = port;
    try {
      selector = Selector.open();
      socketChannel = SocketChannel.open();
      socketChannel.configureBlocking(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    this.stop = true;
  }


  private void doConnect() throws IOException {
    //如果直接连接成功，则注册到多路复用器上，发送请求消息
    if(socketChannel.connect(new InetSocketAddress(host,port))){
      socketChannel.register(selector,SelectionKey.OP_READ);
      timeClientHandler.doWrite(socketChannel);
    }else{
      socketChannel.register(selector,SelectionKey.OP_CONNECT);
    }
  }

  public static void main(String[] args) {
    TimeClient timeClient = new TimeClient(null,40005);
    timeClient.run();
  }
}
