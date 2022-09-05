package com.suchenghe.study.simple.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * @author SuChenghe
 * @date 2018/7/27 17:00
 */
public class MyClient {

    private final String host;
    private final int port;

    public  static SocketChannel socketChannelForSend;

    public MyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        //配置客户端NIO线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();

            //SslContext sslContext = SslContextBuilder.forClient().build();
            SslContext sslContext = getSSLContext();

            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            SslHandler sslHandler = sslContext.newHandler(socketChannel.alloc());
                            socketChannel.pipeline().addLast("sslHandler",sslHandler);
                            socketChannel.pipeline().addLast(new SchClientHandler());

                            socketChannelForSend = socketChannel;
                        }
                    });
            //发起异步连接操作
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //等待客户端链路断开
            channelFuture.channel().closeFuture().sync();
        } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException | KeyManagementException | UnrecoverableKeyException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //退出，释放线程池资源
            eventLoopGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new MyClient("127.0.0.1",61005).start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                // 从控制台输入要发送给服务端的文字
                BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
                //反复向服务端发送消息
                boolean done=false;
                while (!done) {
                    String line= null;
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (line!=null) {
                        socketChannelForSend.write(line+"\n");
                        socketChannelForSend.flush();
                    }else{
                        done=true;
                    }
                }
            }
        }).start();
    }

    // 相关的 jks 文件及其密码定义
    private final static String TRUST_STORE = "C:\\Users\\he524\\sch-client-200625.keystore";
    private final static String TRUST_STORE_PASSWORD = "CHUNYE5241he";
    private SslContext getSSLContext() throws IOException, KeyStoreException,
            CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        //载入jks文件
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(new FileInputStream(TRUST_STORE), TRUST_STORE_PASSWORD.toCharArray());

        //创建并初始化信任库工厂
        String alg = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(alg);
        trustManagerFactory.init(keyStore);

        // 创建并初始化 SSLContext 实例
        SslContext sslContext = SslContextBuilder.forClient().trustManager(trustManagerFactory).build();

        return sslContext;
    }
}
