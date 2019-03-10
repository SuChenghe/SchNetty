package com.suchenghe.netty.common;

/**
 * @author SuChenghe
 * @date 2018/9/6 17:54
 */
public class ByteUtils {
    /**
     * 将16进制字节转换为long类型(低字节在前，高字节在后)
     *
     * @param byteArray
     * @return
     */
    public static long byteTolongByLowToHigh(byte[] byteArray) {
        String[] hexArray = getHexString(byteArray);
        StringBuilder hexStringBuilder = new StringBuilder();
        int length = hexArray.length;
        for (int i = 0; i < length; i++) {
            String hex = hexArray[i];
            if (hex.length() == 1) {
                hexArray[i] = "0" + hex;
            }
            hexStringBuilder.append(hexArray[length - 1 - i]);
        }
        return Long.parseLong(hexStringBuilder.toString(), 16);
    }

    /**
     * 将16进制字节转换为long类型(低字节在后，高字节在前)
     *
     * @param byteArray
     * @return
     */
    public static long byteTolongByHighToLow(byte[] byteArray) {
        String[] hexArray = getHexString(byteArray);
        StringBuilder hexStringBuilder = new StringBuilder();
        int length = hexArray.length;
        for (int i = 0; i < length; i++) {
            String hex = hexArray[i];
            if (hex.length() == 1) {
                hexArray[i] = "0" + hex;
            }
            hexStringBuilder.append(hexArray[i]);
        }
        return Long.parseLong(hexStringBuilder.toString(), 16);
    }

    /**
     * 将long类型转换为字节(低字节在前，高字节在后)
     *
     * @param number
     * @return
     */
    public static byte[] longToByteByLowToHigh(long number) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (number & 0xff);
            number >>= 8;
        }
        return b;
    }

    /**
     * 将long类型转换为字节(高字节在前，低字节在后)
     *
     * @param number
     * @return
     */
    public static byte[] longToByteByHighToLow(long number) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[3 - i] = (byte) (number & 0xff);
            number >>= 8;
        }
        return b;
    }

    /**
     * 获取16进制String数组
     *
     * @param byteArray
     */
    public static String[] getHexString(byte[] byteArray) {
        String[] hexArray = new String[byteArray.length];
        for (int i = 0; i < hexArray.length; i++) {
            hexArray[i] = Integer.toHexString(byteArray[i] & 0xFF);
        }
        return hexArray;
    }

    /**
     * 10进制串转为BCD码
     *
     * @param asc
     * @return
     */
    public static byte[] getBCDfromStr(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        if (len >= 2) {
            len = len / 2;
        }
        //BCD数组
        byte[] bcdArray = new byte[len];
        byte[] ascByteArray = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            j = ascByteArray[2 * p] - '0';
            k = ascByteArray[2 * p + 1] - '0';
            int a = (j << 4) + k;
            byte b = (byte) a;
            bcdArray[p] = b;
        }

        return bcdArray;
    }

    /**
     * BCD码转为10进制
     *
     * @param asc
     * @return
     */
    public static String getStrfromBCD(byte[] asc) {
        StringBuilder timeSb = new StringBuilder();
        for (int p = 0; p < asc.length; p++) {
            byte byteVal = asc[p];
            int val = (0xff & (byteVal >> 4)) * 10 + (0xf & byteVal);
            if (val < 10) {
                timeSb.append("0").append(val);
            } else {
                timeSb.append(val);
            }
        }
        return timeSb.toString();
    }
}
