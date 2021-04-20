package com.fcy.logservice.util.enums;

/**
 * @Describe: 响应状态枚举
 * @Author: fuchenyang
 * @Date: 2021/2/10 15:48
 */
public enum ResponseStatusEnum {

    FAILURE(0, "请求失败"),

    SUCCESS(1, "请求成功");

    private Integer returnCode;
    private String returnMsg;

    ResponseStatusEnum(Integer returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public Integer getReturnCode() {
        return this.returnCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

}
