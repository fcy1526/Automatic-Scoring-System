package com.fcy.logservice.entity;

import com.fcy.logservice.enums.OperationEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 日志实体类
 * @Author: fuchenyang
 * @Date: 2021/3/12 19:08
 */
@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 4264569829442167877L;

    private Integer logId;

    private String sysUserId;

    private String sysTrueName;

    private String opertime;

    private OperationEnum opertype;

    private String type;

    private String opertable;

    private String operation;

}
