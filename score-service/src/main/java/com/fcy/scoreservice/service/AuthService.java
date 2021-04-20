package com.fcy.scoreservice.service;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:14
 */
public interface AuthService {
    /**
     * 根据权限名称，用户id 验证权限
     * @param userId
     * @param btnName
     * @return
     */
    String checkAuth(String userId, String btnName);
}
