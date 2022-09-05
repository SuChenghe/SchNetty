package com.suchenghe.study.tcp_udp.udp.pojo;

import lombok.Data;

/**
 * 设备终端
 *
 * @author SuChenghe
 * @date 2018/8/8 17:29
 */
@Data
public class WgTerminalInfo extends WgBase {
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 控制门1
     */
    private byte door1;
    /**
     * 控制门2
     */
    private byte door2;
    /**
     * 控制门3
     */
    private byte door3;
    /**
     * 控制门4
     */
    private byte door4;
    /**
     * 密码：默认345678
     */
    private String passWord;


}
