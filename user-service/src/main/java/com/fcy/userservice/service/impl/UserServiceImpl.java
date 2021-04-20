package com.fcy.userservice.service.impl;

import com.fcy.userservice.entity.Role;
import com.fcy.userservice.entity.dto.*;
import com.fcy.userservice.entity.vo.*;
import com.fcy.userservice.enums.OperationEnum;
import com.fcy.userservice.entity.SysLog;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.exception.AutomaticScoringSystemException;
import com.fcy.userservice.mapper.ClassMapper;
import com.fcy.userservice.mapper.RoleMapper;
import com.fcy.userservice.mapper.UserMapper;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.BPwdEncoderUtil;
import com.fcy.userservice.util.SystemTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/2/14 20:12
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private ClassMapper classMapper;

    public User findByUserid(String userid) {
        return userMapper.findByUserid(userid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequestDto user) {
        User oldUser = userMapper.findByUserid(user.getUserId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_USERINFO)
                        .operation(user.compare(oldUser))
                        .build());
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StudentUpdateRequestDto student) {
        User oldStudent = userMapper.findByUserid(student.getUserId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_USERINFO)
                        .operation(student.compare(oldStudent))
                        .build());
        userMapper.updateStudent(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TeacherUpdateRequestDto teacher) {
        User oldTeacher = userMapper.findByUserid(teacher.getUserId());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_USERINFO)
                        .operation(teacher.compare(oldTeacher))
                        .build());
        userMapper.update(teacher);
        List<String> teachClasses = classMapper.getClassesById(teacher.getUserId());
        // 保存日志 清空教学班级
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_TEACH_CLASS)
                        .operation("delete t_teach_class")
                        .build());
        classMapper.delete(teacher.getUserId());
        // 保存日志 插入数据
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_TEACH_CLASS)
                        .operation(teacher.getChangeLog("教学班级", teachClasses, teacher.getTeachClasses()))
                        .build());
        classMapper.addList(teacher.getUserId(), teacher.getTeachClasses());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PasswordUpdateRequestDto passwordDto) {
        String password = passwordDto.getPassword();
        if (password.length() < 6 || password.length() > 15) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "密码的长度必须在6~15个字符之间");
        }
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_USERINFO)
                        .operation("update password")
                        .build());
        // BCryt加密
        passwordDto.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        userMapper.updatePwd(passwordDto);
    }

    @Override
    public PageInfo<StudentListRequestVo> getStudentList(Map map) {
        PageInfo<StudentListRequestVo> list = new PageInfo<StudentListRequestVo>(userMapper.getStudentList(buildQuery(map)));
        for (StudentListRequestVo studentListRequestVo : list.getList()) {
            // 设置id列表
            List<Integer> roleIds = studentListRequestVo.getRoles().stream().map(Role::getId).collect(Collectors.toList());
            studentListRequestVo.setRoleIds(roleIds);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addStudent(StudentAddRequestDto user) {
        // 验证user字段合法性
        validUser(user);
        // 密码加密
        user.setPassword(BPwdEncoderUtil.BCryptPassword(user.getPassword()));
//        // 角色列表
//        List<Integer> roleList = user.getRoles().stream().map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return RoleEnum.getId(s);
//            }
//        }).collect(Collectors.toList());
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_USERINFO + ";" + SystemTable.T_USER_ROLE)
                        .operation("insert new student")
                        .build());
        userMapper.addUser(user);
        roleMapper.addOne(user.getUserId(), user.getRoleId());
    }

    @Override
    public StudentUpdateVo getStudentById(String userId) {
        return userMapper.getStudentById(userId);
    }

    @Override
    public TeacherUpdateVo getTeacherById(String userId) {
        return userMapper.getTeacherById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_USERINFO + ";" + SystemTable.T_USER_ROLE)
                        .operation("delete user")
                        .build());
        userMapper.deleteByUserId(userId);
        roleMapper.deleteByUserId(userId);
    }

    @Override
    public PageInfo<TeacherListRequestVo> getTeacherList(Map map) {
        return new PageInfo<TeacherListRequestVo>(userMapper.getTeacherList(buildQuery(map)));
    }

    @Override
    public List<ClassGetTeacherListVo> getTeacherList() {
        return userMapper.getTeacherSimpleList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTeacher(TeacherAddRequestDto user) {
        // 验证user字段合法性
        validUser(user);
        // 密码加密
        user.setPassword(BPwdEncoderUtil.BCryptPassword(user.getPassword()));
        // 保存日志
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_USERINFO + ";" + SystemTable.T_USER_ROLE + ";" + SystemTable.T_TEACH_CLASS)
                        .operation("insert new teacher")
                        .build());
        // 添加教师
        userMapper.addUser(user);
        // 关联班级
        classMapper.addList(user.getUserId(), user.getTeachClasses());
        // 关联角色
        roleMapper.addOne(user.getUserId(), user.getRoleId());
    }



    /**
     * 从dto中获取要查询的字段，填入map中
     * @param map
     */
    public static Map<String, Object> buildQuery(Map map) {
        UserGetListRequestDto userGetListRequest = (UserGetListRequestDto) map.get("query");
        String queryInput = userGetListRequest.getQueryInput();
        String searchType = userGetListRequest.getSearchType();
        // 填充查询条件
        Map<String, Object> query = new HashMap<>();
        query.put(searchType, queryInput);

        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        return query;
    }

    /**
     * 验证user添加对象字段合法性
     * @param user
     */
    public void validUser(User user) {
        String userId = user.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "学号(教师号)不能为空");
        }
        if (null != userMapper.findByUserid(userId)) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "学号(教师号)已存在");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "密码不能为空");
        }
        if (password.length() < 6 || password.length() > 15) {
            throw new AutomaticScoringSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "密码的长度在6~15个字符之间");
        }
    }

}
