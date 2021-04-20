package com.fcy.userservice.service.impl;

import com.fcy.userservice.exception.AutomaticScoringSystemException;
import com.fcy.userservice.mapper.MenuMapper;
import com.fcy.userservice.service.MenuService;
import com.fcy.userservice.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/15 20:30
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> getMenuTree(String userId) {
        List<Map<String, Object>> menuTree = MenuUtil.convertMenuListToTreeList(menuMapper.getMenuList(userId));
        if (CollectionUtils.isEmpty(menuTree)) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "获取菜单失败!");
        }
        return menuTree;
    }
}
