package com.suchenghe.study.unit_test;

import java.util.Random;

/**
 * @author SuChenghe
 * @date 2020/11/2 19:14
 * Description: No Description
 */
public class SchTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            Random random = new Random();
            int newCapacity = random.nextInt(100);
            newCapacity |= newCapacity >>>  1;
            newCapacity |= newCapacity >>>  2;
            newCapacity |= newCapacity >>>  4;
            newCapacity |= newCapacity >>>  8;
            newCapacity |= newCapacity >>> 16;
            newCapacity ++;
        }
        System.out.println("耗时:"+(System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            Random random = new Random();
            int newCapacity = random.nextInt(100);
            newCapacity = newCapacity * 2;
        }
        System.out.println("耗时:"+(System.currentTimeMillis() - startTime2));



    }

}
