package com.suchenghe.study.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SuChenghe
 * @date 2018/10/22 9:49
 */
public class TimeServer {

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    int port = 61005;
    try {
      serverSocket = new ServerSocket(port);
      Socket socket = null;
      while (true){
        socket = serverSocket.accept();
        new Thread(new TimeServerHandler(socket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(null != serverSocket){
        try {
          serverSocket.close();
          serverSocket = null;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
