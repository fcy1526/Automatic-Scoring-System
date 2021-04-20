package com.fcy.userservice.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Describe: 日志消息类
 * @Author: fuchenyang 
 * @Date: 2021/3/14 22:04
 */
@Data
@Builder
public class LogDto {
    // 日志类型 SYS:系统日志 ERR:异常日志
    String type;
    // 日志对象
    Object log;
    
}
