package com.fcy.userservice.enums;

/**
 * @Describe: 操作枚举类
 * @Author: fuchenyang
 * @Date: 2021/3/12 20:13
 */
public enum OperationEnum {

    LOGIN("登录"),

    INSERT("新增"),

    UPDATE("更新"),

    DELETE("删除"),

    SELECT("查询");

    private String type;

    OperationEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
