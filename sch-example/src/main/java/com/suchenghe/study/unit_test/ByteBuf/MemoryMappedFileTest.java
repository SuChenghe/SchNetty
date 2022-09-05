package com.suchenghe.study.unit_test.ByteBuf;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author SuChenghe
 * @date 2020/11/5 18:20
 * Description: No Description
 */
public class MemoryMappedFileTest {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = MemoryMappedFileTest.class.getClassLoader();
        String pathStr = classLoader.getResource("MemeoyMappedFileTest").getPath();
        if(pathStr.startsWith("/")){
            pathStr = pathStr.substring(1);
        }
        Path path = Paths.get(pathStr);

        FileChannel fileChannel = FileChannel.open(path);

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 2, 3);

        if (mappedByteBuffer != null) {
            CharBuffer charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
            System.out.println(charBuffer.toString());
        }


    }

}
