package com.fcy.logservice.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 异常日志实体类
 * @Author: fuchenyang
 * @Date: 2021/3/14 22:01
 */
@Data
public class SysErrorLog implements Serializable {

    private static final long serialVersionUID = -4053945852416423595L;

    private Integer logId;

    private String sysUserId;

    private String sysTrueName;

    private String opertime;

    private String message;
}
