package com.fcy.scoreservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @Describe: 统一异常类
 * @Author: fuchenyang
 * @Date: 2021/3/6 21:58
 */
@Getter
public class AutomaticScoringSystemException extends RuntimeException {

    private static final long serialVersionUID = -1176852292643464942L;

    public AutomaticScoringSystemException(HttpStatus status, String errorMsg) {
        super(errorMsg);
        this.status = status;
        this.errorMsg = errorMsg;
    }

    /**
     * 状态码
     */
    private HttpStatus status;

    /**
     * 错误信息
     */
    private String errorMsg;
}
