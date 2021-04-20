package com.fcy.scoreservice.enums;

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

    public static boolean equals(SexEnum sex1, SexEnum sex2) {
        return sex1.getSex().equalsIgnoreCase(sex2.getSex());
    }
}
