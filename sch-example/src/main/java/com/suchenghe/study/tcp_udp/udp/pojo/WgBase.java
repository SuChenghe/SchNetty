package com.suchenghe.study.tcp_udp.udp.pojo;

import lombok.Data;

/**
 * @author SuChenghe
 * @date 2018/8/15 13:48
 */
@Data
public class WgBase {
    /**
     * 设备序列号
     */
    private long SN;
    /**
     * 功能号
     */
    private byte functionId;
    /**
     * 卡号
     */
    private String cardNum;
    /**
     * 索引号
     */
    private long index;

}
