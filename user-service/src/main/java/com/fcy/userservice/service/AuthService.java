package com.fcy.userservice.service;

import com.fcy.userservice.entity.Authority;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:14
 */
public interface AuthService {

    /**
     * 获取权限列表
     * @return
     */
    List<Authority> getAuthList();

    /**
     * 递归该权限下的子权限id
     * @param authIds
     * @return
     */
    List<String> getIdsByParentIds(List<String> authIds);

    /**
     * 获取所有权限按钮名称 key:按钮名称 value:权限id
     * @return
     */
    Map<String, String> getAllAuthBtn();

    /**
     * 获取当前用户权限按钮
     * @return
     */
    List<String> getUserAuth();

    /**
     * 根据权限名称，用户id 验证权限
     * @param userId
     * @param btnName
     * @return
     */
    String checkAuth(String userId, String btnName);
}
