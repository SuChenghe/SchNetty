package com.suchenghe.study.bio;

import java.io.*;
import java.net.Socket;

/**
 * @author SuChenghe
 * @date 2018/10/23 8:16
 */
public class TimeClient {

  public static void main(String[] args) {
    int port = 61005;
    BufferedReader in = null;
    PrintWriter out = null;
    Socket socket = null;
    try {
      socket = new Socket("127.0.0.1", port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
      out.println("这是客户端的请求");
      String body = in.readLine();
      System.out.println("读取到数据:" + body);

    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      dealException(socket,in,out);
    }
  }


  /**
   * 异常处理
   * @param socket
   * @param in
   * @param out
   */
  public static void dealException(Socket socket,BufferedReader in,PrintWriter out){
    if(null != out){
      out.close();
      out = null;
    }
    if(null != in){
      try {
        in.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    in = null;
    if(null != socket){
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    socket = null;
  }
}
