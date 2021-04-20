package com.fcy.userservice.controller;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.common.BtnAuthName;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.PasswordUpdateRequestDto;
import com.fcy.userservice.entity.dto.UserSetRoleRequestDto;
import com.fcy.userservice.entity.dto.UserUpdateRequestDto;
import com.fcy.userservice.entity.vo.UserInfoVo;
import com.fcy.userservice.service.RoleService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/13 20:08
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户数据 按钮要求：PERSON_INFO
     * @return
     */
    @GetMapping("")
    @AuthCheck(btnName = BtnAuthName.PERSON_INFO)
    public Result<Object> getUserInfo() {
        User user = userService.findByUserid(AuthUtil.getCurrentUserid());
        UserInfoVo userinfo = new UserInfoVo();
        BeanUtils.copyProperties(user, userinfo);
        return ResultUtil.success(userinfo);
    }

    /**
     * 更新用户个人信息 按钮要求：PERSON_EDIT
     * @param user
     * @return
     */
    @PostMapping("")
    @AuthCheck(btnName = BtnAuthName.PERSON_EDIT)
    public Result<Object> updateUserInfo(@RequestBody UserUpdateRequestDto user) {
        userService.update(user);
        UserInfoVo userinfo = new UserInfoVo();
        BeanUtils.copyProperties(user, userinfo);
        return ResultUtil.success(userinfo);
    }

    /**
     * 修改用户密码 按钮要求：PERSON_PWD
     * @param passwordDto
     * @return
     */
    @PostMapping("pwd")
    @AuthCheck(btnName = BtnAuthName.PERSON_PWD)
    public Result<Object> updatePasswod(@RequestBody PasswordUpdateRequestDto passwordDto) {
        userService.update(passwordDto);
        return ResultUtil.success();
    }

    /**
     * 根据id删除用户 按钮要求：STUDENT_DELETE
     * @param userId
     * @return
     */
    @DeleteMapping("{userId}")
    @AuthCheck(btnName = BtnAuthName.STUDENT_DELETE)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> deleteByUserId(@PathVariable("userId") String userId) {
        userService.deleteByUserId(userId);
        return ResultUtil.success();
    }

    /**
     * 分配角色 按钮要求：STUDENT_ROLE
     * @param userSetRoleRequest
     * @return
     */
    @PutMapping("")
    @AuthCheck(btnName = BtnAuthName.STUDENT_ROLE)
    @PreAuthorize("hasAnyAuthority('Admin', 'Teacher')")
    public Result<Object> setRole(@RequestBody UserSetRoleRequestDto userSetRoleRequest) {
        roleService.setRole(userSetRoleRequest);
        return ResultUtil.success();
    }
}
