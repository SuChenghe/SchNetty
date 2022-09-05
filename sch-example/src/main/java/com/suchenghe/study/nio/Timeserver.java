package com.suchenghe.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author SuChenghe
 * @date 2018/10/28 11:04
 */
public class Timeserver implements Runnable {
  /**
   * 多路复用器
   */
  private Selector selector;
  /**
   * 通道
   */
  private ServerSocketChannel serverSocketChannel;
  private volatile boolean stop;
  private TimeServerHandler timeServerHandler = new TimeServerHandler();

  @Override
  public void run() {
    while (!stop) {
      try {
        //selector休眠时间
        selector.select(1000);
        Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
        SelectionKey selectionKey = null;
        while (selectionKeyIterator.hasNext()) {
          selectionKey = selectionKeyIterator.next();
          selectionKeyIterator.remove();
          try {
            timeServerHandler.handelInput(selector, selectionKey);
          } catch (IOException e) {
            if(null != selectionKey) {
              selectionKey.cancel();
              if (null != selectionKey.channel()) {
                selectionKey.channel().close();
              }
            }
          }
        }
      }catch (Exception e){
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

    /**
     * 初始化多路复用器，绑定监听端口
     */
  public Timeserver(int port){
      try {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定监听端口
        //backlog:输入连接指示（对连接的请求）的最大队列长度。如果队列满时收到连接指示，则拒绝该连接。
        serverSocketChannel.bind(new InetSocketAddress(port), 1);
        //将channel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("MultiplexerTimeServer,port:" + port + "已启动");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  public void stop() {
    this.stop = true;
  }

  public static void main(String[] args) {
    Timeserver timeserver = new Timeserver(61006);
    timeserver.run();
  }
}
