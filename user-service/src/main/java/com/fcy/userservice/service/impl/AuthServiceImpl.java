package com.fcy.userservice.service.impl;

import com.fcy.userservice.entity.Authority;
import com.fcy.userservice.mapper.AuthMapper;
import com.fcy.userservice.service.AuthService;
import com.fcy.userservice.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Authority> getAuthList() {
        return authMapper.getAuthList();
    }

    @Override
    public List<String> getIdsByParentIds(List<String> authIds) {
        List<String> newAuthIds = authMapper.getIdsByParentIds(authIds);
        if (!CollectionUtils.isEmpty(newAuthIds)) {
            List<String> ids = getIdsByParentIds(newAuthIds);
            if (!CollectionUtils.isEmpty(ids)) newAuthIds.addAll(ids);
        }
        return newAuthIds;
    }

    @Override
    public Map<String, String> getAllAuthBtn() {
        List<Authority> authList = authMapper.getAllAuthBtn();
        Map<String, String> authMap = new HashMap<>();
        authList.stream().forEach(authority ->
            authMap.put(authority.getBtnName(), authority.getAuthId()));
        return authMap;
    }

    @Override
    public List<String> getUserAuth() {
        return authMapper.getUserAuth(AuthUtil.getCurrentUserid());
    }

    @Override
    public String checkAuth(String userId, String btnName) {
        return authMapper.checkAuth(userId, btnName);
    }
}
