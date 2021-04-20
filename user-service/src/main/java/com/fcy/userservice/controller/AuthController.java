package com.fcy.userservice.controller;

import com.fcy.userservice.entity.Authority;
import com.fcy.userservice.service.AuthService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:12
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 获取权限列表 type:list/tree list:返回权限list，tree:返回权限树 权限要求：管理员
     * @return
     */
    @GetMapping("{type}")
    @PreAuthorize("hasAuthority('Admin')")
    public Result<Object> getAuthList(@PathVariable("type") String type) {
        List<Authority> authorities = authService.getAuthList();
        if (type.equalsIgnoreCase("tree")) {
            // 返回权限树
            return ResultUtil.success(AuthUtil.convertAuthorityListToTreeList(authorities));
        } else {
            return ResultUtil.success(authorities);
        }
    }

    /**
     * 获取所有权限按钮名称
     * @return
     */
    @GetMapping("")
    public Result<Object> getAllAuthBtn() {
        return ResultUtil.success(authService.getAllAuthBtn());
    }

    /**
     * 获取当前用户权限按钮
     * @return
     */
    @GetMapping("user")
    public Result<Object> getUserAuth() {
        return ResultUtil.success(authService.getUserAuth());
    }
}
