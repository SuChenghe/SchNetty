package com.suchenghe.study.bio;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SuChenghe
 * @date 2018/10/22 9:56
 */
public class TimeServerHandler implements Runnable{
  private Socket socket;

  public TimeServerHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    BufferedReader in = null;
    PrintWriter out = null;
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
      String body;
      while (true){
        body = in.readLine();
        if(null == body){
          break;
        }
        System.out.println("read data:"+body);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        out.println("Time is:"+sdf.format(new Date()));
      }
    } catch (IOException e) {
      e.printStackTrace();
      dealException(in,out);
    }
  }

  /**
   * 异常处理
   * @param in
   * @param out
   */
  public void dealException(BufferedReader in,PrintWriter out){
    if(null != in){
      try {
        in.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    if(null != out){
      out.close();
      out = null;
    }
    if(null != this.socket){
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.socket = null;
    }
  }

}
