package com.fcy.scoreservice.service.impl;

import com.fcy.scoreservice.mapper.AuthMapper;
import com.fcy.scoreservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:16
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public String checkAuth(String userId, String btnName) {
        return authMapper.checkAuth(userId, btnName);
    }
}
