package com.suchenghe.study.reactor.mainAndsubReactor;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuChenghe
 * @date 2020/6/13 21:02
 */
public class SubReactor implements Runnable {

    final Selector mySelector;

    //多线程处理业务逻辑
    int workCount = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(workCount);

    public SubReactor() throws Exception {
        // 每个SubReactor 一个selector 
        this.mySelector = SelectorProvider.provider().openSelector();
    }

    /**
     * 注册chanel
     *
     * @param sc
     * @throws Exception
     */
    public void registerChannel(SocketChannel sc) throws Exception {
        sc.configureBlocking(false);
        sc.register(mySelector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                //每个SubReactor自己做事件分派处理读写事件
                this.mySelector.select(1000);
                Set<SelectionKey> keys = this.mySelector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SelectionKey key) {
        //任务异步处理
        executorService.submit(() -> process(key));
    }

    private void write(SelectionKey key) {
        //任务异步处理
        executorService.submit(() -> process(key));
    }

    /**
     * task 业务处理
     */
    public void process(SelectionKey selectionKey) {
        //Read the data
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer readByteBuffer = ByteBuffer.allocate(1024);
        int readBytes = 0;
        try {
            readBytes = socketChannel.read(readByteBuffer);
            if (readBytes > 0) {
                //将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作。
                readByteBuffer.flip();
                byte[] bytes = new byte[readByteBuffer.remaining()];
                readByteBuffer.get(bytes);
                String body = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("服务端接收到消息:" + body);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String result = "时间是:" + sdf.format(new Date());
                doWrite(socketChannel, result);
            } else if (readBytes < 0) {
                socketChannel.close();
                System.out.println("客户端断开连接");
            } else {
                //忽略
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将消息发送给客户端
     *
     * @param socketChannel
     * @param result
     * @throws IOException
     */
    private void doWrite(SocketChannel socketChannel, String result) throws IOException {
        if (!StringUtils.isEmpty(result)) {
            byte[] bytes = result.getBytes("GB2312");
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }

}
