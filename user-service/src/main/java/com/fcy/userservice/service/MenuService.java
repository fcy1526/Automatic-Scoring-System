package com.fcy.userservice.service;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/15 20:28
 */
public interface MenuService {
    /**
     * 根据用户id获取菜单树
     * @param currentUserid
     * @return
     */
    List<Map<String, Object>> getMenuTree(String currentUserid);
}
