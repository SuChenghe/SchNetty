package com.suchenghe.study.tcp_udp.udp.em;

/**
 * 功能号-枚举
 *
 * @author SuChenghe
 * @date 2018/8/9 11:18
 */
public enum WgCmdTypeEnum {

    /**
     * 增加权限-下发卡号到控制器
     */
    ADD_PERMISSIONS((byte)0x50,"增加权限"),
    /**
     * 删除权限-从控制器删除卡号
     */
    DELETE_PERMISSIONS((byte)0x52,"删除权限"),
    /**
     * 控制器自动上传最新状态
     */
    CONTRO_STATUS((byte)0x20,"控制器状态"),
    /**
     * 根据索引号获取记录信息
     */
    ATTENDANCE_INDEX((byte)0xB0,"获取指定索引号的记录");

    private byte cmdType;
    private String cmdDesc;

    WgCmdTypeEnum(byte cmdType, String cmdDesc) {
        this.cmdType = cmdType;
        this.cmdDesc = cmdDesc;
    }

    public byte getCmdType() {
        return cmdType;
    }

    public void setCmdType(byte cmdType) {
        this.cmdType = cmdType;
    }

    public String getCmdDesc() {
        return cmdDesc;
    }

    public void setCmdDesc(String cmdDesc) {
        this.cmdDesc = cmdDesc;
    }
}
