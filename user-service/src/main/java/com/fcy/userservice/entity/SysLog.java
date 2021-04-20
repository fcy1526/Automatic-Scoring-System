package com.fcy.userservice.entity;

import com.fcy.userservice.enums.OperationEnum;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @Describe: 日志实体类
 * @Author: fuchenyang
 * @Date: 2021/3/12 19:08
 */
@Data
@SuperBuilder
public class SysLog extends Log implements Serializable {

    private static final long serialVersionUID = -3516882743293729494L;

    private OperationEnum opertype;

    private String opertable;

    private String operation;

}
