package com.fcy.userservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 权限实体类
 * @Author: fuchenyang 
 * @Date: 2021/3/15 20:02
 */
@Data
public class Authority implements Serializable {

    private static final long serialVersionUID = 6951077411606726376L;
    /**
     * 权限id
     */
    private String authId;
    /**
     * 权限名称
     */
    private String authName;
    /**
     * 路径
     */
    private String path;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 级数
     */
    private Integer grade;
    /**
     * 按钮名称
     */
    private String btnName;
}
