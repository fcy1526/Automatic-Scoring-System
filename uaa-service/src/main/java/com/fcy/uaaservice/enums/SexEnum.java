package com.fcy.uaaservice.enums;

/**
 * @Describe: 性别枚举
 * @Author: fuchenyang
 * @Date: 2021/2/14 19:52
 */
public enum SexEnum {

    MAN("男"),

    WOMAN("女");

    private String sex;

    SexEnum(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return this.sex;
    }
}
