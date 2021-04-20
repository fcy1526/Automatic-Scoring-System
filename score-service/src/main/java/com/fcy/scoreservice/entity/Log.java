package com.fcy.scoreservice.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @Describe: 日志类
 * @Author: fuchenyang
 * @Date: 2021/3/22 20:28
 */
@Data
@SuperBuilder
public class Log {

    @Tolerate
    public Log() {}

    private String logId;

    private String sysUserId;

    private String sysTrueName;

    private String opertime;

}
