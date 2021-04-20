package com.fcy.userservice.entity;

import com.fcy.userservice.enums.SexEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.fcy.userservice.common.Constants.CHANGE_LOG_TEMPLATE;

/**
 * @Describe: 用户实体类
 * @Author: fuchenyang
 * @Date: 2021/2/14 19:52
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -3483248093129794209L;

    /**
     * 学号
     */
    private String userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 比较前后更新的数据
     * @param oldUser
     * @return
     */
    public String compare(User oldUser) {
        StringBuilder changeLog = new StringBuilder("");

        if (!Objects.equals(oldUser.getTrueName(), trueName)) {
            changeLog.append(getChangeLog("真实姓名", oldUser.getTrueName(), trueName));
        }
        if (!SexEnum.equals(oldUser.getSex(), sex)) {
            changeLog.append(getChangeLog("性别", oldUser.getSex().getSex(), sex.getSex()));
        }
        return changeLog.toString();
    }

    /**
     * 获取字段变更记录
     * @param field
     * @param oldVal
     * @param newVal
     * @return
     */
    public String getChangeLog(String field, Object oldVal, Object newVal) {
        return String.format(CHANGE_LOG_TEMPLATE, field, oldVal, newVal);
    }
}
