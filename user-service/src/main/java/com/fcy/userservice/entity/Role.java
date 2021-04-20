package com.fcy.userservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.fcy.userservice.common.Constants.CHANGE_LOG_TEMPLATE;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:39
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -8047231842883533451L;

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String describe;

    /**
     * 是否为系统角色
     */
    private Integer isSysRole;

    /**
     * 权限列表
     */
    private List<Authority> authorities;

    /**
     * 比较前后更新的数据
     * @param oldRole
     * @return
     */
    public String compare(Role oldRole) {
        StringBuilder changeLog = new StringBuilder("");

        if (!Objects.equals(oldRole.getName(), name)) {
            changeLog.append(getChangeLog("角色名称", oldRole.getName(), name));
        }
        if (!Objects.equals(oldRole.getDescribe(), describe)) {
            changeLog.append(getChangeLog("角色描述", oldRole.getDescribe(), describe));
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

    @Override
    public String toString() {
        return "Role{" +
                "角色ID = " + id +
                ", 角色名称 = '" + name + '\'' +
                ", 角色描述 = '" + describe + '\'' +
                '}';
    }
}
