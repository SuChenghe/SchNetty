package com.suchenghe.study.unit_test.ByteBuf;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author SuChenghe
 * @date 2020/11/5 18:20
 * Description:
 * 使用内存映射文件的主要优势在于，它有很高的 I/O 性能，特别是对于索引这样的小文件来说，
 * 由于文件内存被直接映射到一段虚拟内存上，访问内存映射文件的速度要快于普通的读写文件速度
 */
public class MemoryMappedFileWriteTest {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = MemoryMappedFileWriteTest.class.getClassLoader();
        String pathStr = classLoader.getResource("MemeoyMappedFileTest2").getPath();
        if(pathStr.startsWith("/")){
            pathStr = pathStr.substring(1);
        }
        Path path = Paths.get(pathStr);

        FileChannel fileChannel = FileChannel.open(path,
                StandardOpenOption.READ, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING);

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
        if (mappedByteBuffer != null) {
            mappedByteBuffer.put(Charset.forName("UTF-8").encode("111"));
            mappedByteBuffer.put(Charset.forName("UTF-8").encode("222"));
        }
        mappedByteBuffer.position(5);
        if (mappedByteBuffer != null) {
            mappedByteBuffer.put(Charset.forName("UTF-8").encode("3333"));
        }
    }

}
