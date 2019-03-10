package com.suchenghe.netty.udp.pojo;

import lombok.Data;

/**
 * 刷卡记录
 *
 * @author SuChenghe
 * @date 2018/8/8 17:29
 */
@Data
public class WgAttendance extends WgBase {
    /**
     * 记录类型
     * 0=无记录
     * 1=刷卡记录
     * 2=门磁,按钮, 设备启动, 远程开门记录
     * 3=报警记录
     */
    private int cardRecordType;
    /**
     * 有效性
     */
    private int passState;
    /**
     * 门号
     */
    private int door;
    /**
     * 进门/出门（1 进门，2出门）
     */
    private int passWay;
    /**
     * 刷卡时间
     */
    private String passTime;
    /**
     * 原因代码
     */
    private int reasonCode;

    /**
     * @return
     * @Data注解会重写toString()方法，但不会输出继承父类的成员属性值，所以重写下
     */
    @Override
    public String toString() {
        return "WgAttendance{" +
                "SN= " + this.getSN() +
                ", functionId=" + this.getFunctionId() +
                ", cardNum='" + this.getCardNum() + '\'' +
                ", index=" + this.getIndex() +
                ", cardRecordType=" + cardRecordType +
                ", passState=" + passState +
                ", door=" + door +
                ", passWay=" + passWay +
                ", passTime='" + passTime + '\'' +
                ", reasonCode=" + reasonCode +
                '}';
    }
}
