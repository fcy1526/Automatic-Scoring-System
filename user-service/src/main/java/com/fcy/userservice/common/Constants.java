package com.fcy.userservice.common;

/**
 * @Describe: 常数类
 * @Author: fuchenyang
 * @Date: 2021/3/1 20:53
 */
public class Constants {

    // clientid:secret
    public static final String AUTHORIZATION = "Basic dXNlcnNlcnZpY2U6MTIzNDU2";
    // grant_type
    public static final String GRANT_TYPE = "password";
    // 日期格式
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    // 数据更新日志格式
    public static final String CHANGE_LOG_TEMPLATE = "[%s],修改前:%s,修改后:%s;\n";
    // 系统日志
    public static final String SYS_LOG = "sys";
    // 异常日志
    public static final String SYS_ERR_LOG = "err";
    // 系统角色
    public static final Integer SYS_ROLE = 1;
    // 非系统角色
    public static final Integer NOT_SYS_ROLE = 0;
    // 学生角色 Student_前缀
    public static final String STUDENT_PRE = "Student_";
    // 是否为学生角色
    public static final Integer IS_STUDENT_ROLE = 1;
    // 个人中心权限id字符串
    public static final String PERSONAL_AUTH_STR = "1,109,10901,10902,10903,4,403";
}
