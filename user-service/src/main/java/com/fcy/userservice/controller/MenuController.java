package com.fcy.userservice.controller;

import com.fcy.userservice.entity.User;
import com.fcy.userservice.mapper.UserMapper;
import com.fcy.userservice.service.MenuService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Describe: 菜单控制器
 * @Author: fuchenyang
 * @Date: 2021/3/15 20:25
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取菜单树
     * @return
     */
    @GetMapping("")
    public Result<Object> getMenuTree() {
        User currentUser = userMapper.findByUserid(AuthUtil.getCurrentUserid());
        return ResultUtil.success(menuService.getMenuTree(currentUser.getUserId()));
    }
}
