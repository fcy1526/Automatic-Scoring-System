package com.fcy.uaaservice.mapper;

import com.fcy.uaaservice.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUserid(String userId);
}
