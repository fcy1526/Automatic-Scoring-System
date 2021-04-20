package com.fcy.userservice.enums;

/**
 * @Describe: 系统角色枚举类
 * @Author: fuchenyang
 * @Date: 2021/3/19 0:08
 */
public enum RoleEnum {

    STUDENT(1, "普通学生"),

    STUDENT_GROUP_LEADER(2, "组长"),

    STUDENT_STUDY_COMMITTEE(3, "学习委员"),

    TEACHER(4, "教师"),

    ADMIN(5, "管理员");

    private Integer id;

    private String describe;

    RoleEnum(Integer id, String describe) {
        this.id = id;
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public Integer getId() {
        return id;
    }

    /**
     * 将角色名称转换为角色id
     * @param describe
     * @return
     */
    public static Integer getId(String describe) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getDescribe().equalsIgnoreCase(describe)) {
                return roleEnum.getId();
            }
        }
        return null;
    }
}
