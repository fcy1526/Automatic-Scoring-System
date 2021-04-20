package com.fcy.logservice.util.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 响应实体类
 * @Author: fuchenyang
 * @Date: 2021/2/10 15:33
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4953929293308126624L;

    /**
     * 状态码
     */
    private Integer returnCode;
    /**
     * 响应消息
     */
    private String returnMsg;
    /**
     * 响应数据
     */
    private T data;

}
