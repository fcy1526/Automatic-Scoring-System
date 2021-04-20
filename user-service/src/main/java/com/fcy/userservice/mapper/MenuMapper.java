package com.fcy.userservice.mapper;

import com.fcy.userservice.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/15 20:30
 */
@Mapper
public interface MenuMapper {

    List<Menu> getMenuList(String userId);
}
