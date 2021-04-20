package com.fcy.userservice.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Describe: 日志类
 * @Author: fuchenyang
 * @Date: 2021/3/22 20:28
 */
@Data
@SuperBuilder
public class Log {

    private String logId;

    private String sysUserId;

    private String sysTrueName;

    private String opertime;

}
