package com.fcy.userservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

import static com.fcy.userservice.common.Constants.CHANGE_LOG_TEMPLATE;

/**
 * @Describe:  班级实体类
 * @Author: fuchenyang
 * @Date: 2021/3/18 0:01
 */
@Data
public class Class implements Serializable {

    private static final long serialVersionUID = 1423547853819108229L;
    // 班级id
    private Integer classId;
    // 班级名称
    private String name;

    /**
     * 比较前后更新的数据
     * @param oldClass
     * @return
     */
    public String compare(Class oldClass) {
        StringBuilder changeLog = new StringBuilder("");

        if (!Objects.equals(oldClass.getName(), name)) {
            changeLog.append(getChangeLog("班级名称", oldClass.getName(), name));
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
