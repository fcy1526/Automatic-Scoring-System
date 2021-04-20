package com.fcy.userservice.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @Describe: 异常日志实体类
 * @Author: fuchenyang
 * @Date: 2021/3/14 22:01
 */
@Data
@SuperBuilder
public class SysErrorLog extends Log implements Serializable {

    private static final long serialVersionUID = -6038876827138305344L;

    private String message;
}
