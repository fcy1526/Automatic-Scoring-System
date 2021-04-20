package com.fcy.scoreservice.entity;

import com.fcy.scoreservice.enums.OperationEnum;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @Describe: 日志实体类
 * @Author: fuchenyang
 * @Date: 2021/3/12 19:08
 */
@Data
@SuperBuilder
public class SysLog extends Log implements Serializable {

    @Tolerate
    public SysLog() {
        super();
    }

    private static final long serialVersionUID = -3516882743293729494L;

    private OperationEnum opertype;

    private String type;

    private String opertable;

    private String operation;

}
