package com.fcy.scoreservice.enums;

/**
 * @Describe: 课程评分进度枚举类
 * @Author: fuchenyang
 * @Date: 2021/3/28 20:46
 */
public enum CourseStatus {

    WAIT("未开始"),

    PROCESS("进行中"),

    FINISH("结束"),

    COMPLETE("已完成");

    private String status;

    CourseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
