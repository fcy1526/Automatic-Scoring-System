package com.fcy.userservice.service.impl;

import com.fcy.userservice.entity.Class;
import com.fcy.userservice.entity.SysLog;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.ClassAddRequestDto;
import com.fcy.userservice.entity.dto.ClassSetTeacherRequestDto;
import com.fcy.userservice.entity.dto.ClassUpdateRequestDto;
import com.fcy.userservice.entity.vo.ClassListVo;
import com.fcy.userservice.enums.OperationEnum;
import com.fcy.userservice.mapper.ClassMapper;
import com.fcy.userservice.mapper.UserMapper;
import com.fcy.userservice.service.ClassService;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.SystemTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.fcy.userservice.common.Constants.CHANGE_LOG_TEMPLATE;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/18 22:58
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Class> getClassList() {
        return classMapper.getClassList();
    }

    @Override
    public List<Class> getClassListByTeacherId() {
        return classMapper.getClassListByTeacherId(AuthUtil.getCurrentUserid());
    }

    @Override
    public Integer getTotalCount(Integer classId) {
        return classMapper.getTotalCount(classId);
    }

    @Override
    public String getClassName(Integer classId) {
        return classMapper.getClassName(classId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String userId) {
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_TEACH_CLASS)
                        .operation("delete t_teach_class")
                        .build());
        classMapper.delete(userId);
    }

    @Override
    public PageInfo<ClassListVo> query(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ClassListVo> classList = new PageInfo<ClassListVo>(classMapper.query(name));
        List<ClassListVo> classListVo = classList.getList();
        classListVo.stream().forEach(ClassListVo::setTeacherId);
        return classList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addClass(ClassAddRequestDto addRequest) {
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_CLASS)
                        .operation("???????????????????????????: [" + addRequest.getName() + "]")
                        .build());
        classMapper.addClass(addRequest);
    }

    @Override
    public Class getClassById(Integer classId) {
        return classMapper.getClassById(classId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateClass(ClassUpdateRequestDto updateRequest) {
        Class oldClass = classMapper.getClassById(updateRequest.getClassId());
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_CLASS)
                        .operation(updateRequest.compare(oldClass))
                        .build());
        classMapper.updateClass(updateRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteClassById(Integer classId) {
        Class deleteClass = classMapper.getClassById(classId);
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_CLASS)
                        .operation("???????????????????????????: [" + deleteClass.getName() + "]")
                        .build());
        classMapper.deleteClassById(classId);
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_TEACH_CLASS)
                        .operation("?????????????????????????????????: [" + deleteClass.getName() + "]")
                        .build());
        // ??????????????????
        classMapper.deleteTeacher(classId);
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.UPDATE)
                        .opertable(SystemTable.T_USERINFO)
                        .operation("?????????????????????????????????: [" + deleteClass.getName() + "]")
                        .build());
        userMapper.deleteClassId(classId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setTeacher(ClassSetTeacherRequestDto classSetTeacher) {
        List<User> teachList = classMapper.getTeacherById(classSetTeacher.getClassId());
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.DELETE)
                        .opertable(SystemTable.T_USER_ROLE)
                        .operation("delete user_role")
                        .build());
        // ????????????????????????
        classMapper.deleteTeacher(classSetTeacher.getClassId());
        // ????????????
        logService.logBuild(
                SysLog.builder()
                        .opertype(OperationEnum.INSERT)
                        .opertable(SystemTable.T_TEACH_CLASS)
                        .operation(getChangeLog(teachList.stream().map(User::getTrueName).collect(Collectors.toList()),
                                classSetTeacher.getTeacherIdList()))
                        .build());
        // ????????????
        classMapper.setTeacher(classSetTeacher.getClassId(), classSetTeacher.getTeacherIdList());
    }

    /**
     * ????????????????????????
     * @param oldVal
     * @param newVal
     * @return
     */
    public String getChangeLog(Object oldVal, Object newVal) {
        return String.format(CHANGE_LOG_TEMPLATE, "??????", oldVal, newVal);
    }

//    public Function<ClassListVo, ClassListVo> convert() {
//        return classListVo -> {
//            classListVo.setTeacherIdList(classListVo.getTeachers().stream().map(User::getUserId).collect(Collectors.toList()));
//            return classListVo;
//        };
//    }

}
