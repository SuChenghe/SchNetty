//package com.suchenghe.study.back.client;
//
//import com.suchenghe.study.back.proto.SchClientProtobuf;
//import com.suchenghe.study.back.proto.SchServerProtobuf;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.protobuf.ProtobufDecoder;
//import io.netty.handler.codec.protobuf.ProtobufEncoder;
//
//import java.net.InetSocketAddress;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author SuChenghe
// * @date 2018/7/27 17:00
// */
//public class SchClient {
//
//    /**
//     * 定时任务线程
//     */
//    public static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
//    public static ScheduledExecutorService heartBeatsThreadPool = Executors.newScheduledThreadPool(5);
//
//    private static ConcurrentHashMap<String,Channel> hashMap = new ConcurrentHashMap<>();
//
//    private final String host;
//    private final int port;
//
//    public SchClient(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    public void start() throws InterruptedException {
//        //配置客户端NIO线程组
//        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//
//        try{
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(eventLoopGroup)
//                    .channel(NioSocketChannel.class)
//                    .remoteAddress(new InetSocketAddress(host,port))
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            socketChannel.pipeline()
//                            .addLast("encoder", new ProtobufEncoder())
//                            .addLast("decoder", new ProtobufDecoder(SchServerProtobuf.Response.getDefaultInstance()))
//                            .addLast(new SchClientHandler());
//                        }
//                    });
//            //发起异步连接操作
//            ChannelFuture channelFuture = bootstrap.connect().sync();
//            Channel channel = channelFuture.channel();
//            hashMap.put(String.valueOf(System.currentTimeMillis()),channel);
//            channel.closeFuture().sync();
//        } finally {
//            //退出，释放线程池资源
//            eventLoopGroup.shutdownGracefully().sync();
//        }
//
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//
//        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    SchClient schClient = new SchClient("127.0.0.1",61005);
//                    //客户端启动
//                    schClient.start();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },0L,3000L, TimeUnit.SECONDS);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                while (true){
//                    Set<Map.Entry<String,Channel>> entries =  hashMap.entrySet();
//                    for(Map.Entry<String,Channel> entry : entries){
//                        Channel channel = entry.getValue();
//                        //protobuf
//                        SchClientProtobuf.Request request = SchClientProtobuf.Request.newBuilder()
//                                .setSop("5A").setCmd("HEART_BEAT").setData("This is My Heartbeat").build();
//                        try {
//                            channel.writeAndFlush(request).sync();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(35*1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//}
