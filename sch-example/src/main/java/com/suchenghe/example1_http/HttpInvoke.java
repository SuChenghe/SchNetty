package com.suchenghe.study.example1_http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpInvoke {

    private final static int num = 1;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(num);

    public static void main(String[] args) {
        for (int i = 0; i < num; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            TimeUnit.MILLISECONDS.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        doGet("http://localhost:8899/test1");
                        return;
                    }
                }
            });
        }
    }

    public static String doGet(String httpUrl){
        System.out.println("发起调用");
        //链接
        HttpURLConnection connection = null;
//        InputStream inputStream = null;
//        BufferedReader bufferedReader = null;
//        StringBuffer result = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                //inputStream = connection.getInputStream();
//                if (null != inputStream) {
//                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                    String temp = null;
//                    while (null != (temp = bufferedReader.readLine())) {
//                        result.append(temp);
//                    }
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (null != bufferedReader) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (null != inputStream) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            //关闭远程连接
            //connection.disconnect();
        }
//        return result.toString();
        return null;
    }

}
