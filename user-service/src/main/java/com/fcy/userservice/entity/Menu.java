package com.fcy.userservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 菜单实体类
 * @Author: fuchenyang
 * @Date: 2021/3/15 20:07
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = -56875562131766680L;
    // 菜单id
    private String menuId;
    // 菜单名称
    private String menuName;
    // 菜单路径
    private String menuHref;
    // 父id
    private String parentId;
}
