package com.fcy.userservice.mapper;

import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.dto.RoleAddRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 23:44
 */
@Mapper
public interface RoleMapper {

    List<Role> getRoleList();

    Role getRoleById(Integer roleId);

    List<Role> getStudentRoleList();

    void addOne(String userId, Integer roleId);

    void addList(String userId, List<Integer> roleList);

    void deleteByUserId(String userId);

    List<String> getRoleListById(String userId);

    void clearAuths(Integer roleId);

    void distributeAuthToRole(Integer roleId, String authIds);

    void deleteRoleAuth(Integer roleId, List<String> authIds);

    Integer checkRoleIsSystem(Integer roleId);

    void update(Role role);

    void deleteRoleById(Integer roleId);

    void addRole(RoleAddRequestDto roleAddRequest);
}
