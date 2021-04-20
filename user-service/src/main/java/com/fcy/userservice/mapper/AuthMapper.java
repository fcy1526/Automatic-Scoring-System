package com.fcy.userservice.mapper;

import com.fcy.userservice.entity.Authority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:16
 */
@Mapper
public interface AuthMapper {

    List<Authority> getAuthList();

    List<String> getIdsByParentIds(List<String> authIds);

    List<Authority> getAllAuthBtn();

    List<String> getUserAuth(String userId);

    String checkAuth(String userId, String btnName);
}
