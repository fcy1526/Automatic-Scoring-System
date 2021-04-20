package com.fcy.logservice.entity.dto;

import lombok.Data;

/**
 * @Describe: 日志消息类
 * @Author: fuchenyang 
 * @Date: 2021/3/14 22:04
 */
@Data
public class LogDto {

    String type;

    Object log;
    
}
