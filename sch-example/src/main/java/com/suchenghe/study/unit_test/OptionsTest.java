package com.suchenghe.study.unit_test;

import io.netty.channel.ChannelOption;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author SuChenghe
 * @date 2020/10/26 13:57
 * Description: No Description
 */
public class OptionsTest {

    static final Map.Entry<ChannelOption<?>, Object>[] EMPTY_OPTION_ARRAY = new Map.Entry[0];

    private static final Map<ChannelOption<?>, Object> options = new LinkedHashMap<ChannelOption<?>, Object>();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            options.put(ChannelOption.valueOf("KEY"+i), i);
        }

        System.out.println("数据初始化完毕");

        Iterator<Map.Entry<ChannelOption<?>, Object>> iterator = options.entrySet().iterator();

        long startTime = System.currentTimeMillis();
        while (iterator.hasNext()){
            Map.Entry<ChannelOption<?>, Object> entry = iterator.next();
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
        System.out.println("耗时："+ (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Map.Entry<ChannelOption<?>, Object>[] newOptionsArray2
                = options.entrySet().toArray(new Map.Entry[]{});
        for (Map.Entry<ChannelOption<?>, Object> e : newOptionsArray2) {
            System.out.println(e.getKey() + "," + e.getValue());
        }
        System.out.println("耗时："+ (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Map.Entry<ChannelOption<?>, Object>[] newOptionsArray
                = options.entrySet().toArray(EMPTY_OPTION_ARRAY);
        for (Map.Entry<ChannelOption<?>, Object> e : newOptionsArray) {
            System.out.println(e.getKey() + "," + e.getValue());
        }
        System.out.println("耗时："+ (System.currentTimeMillis() - startTime));

    }
}
