package com.fcy.scoreservice.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/25 21:16
 */
@Mapper
public interface AuthMapper {

    String checkAuth(String userId, String btnName);
}
