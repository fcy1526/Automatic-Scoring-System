package com.fcy.userservice.exception.advice;

import com.fcy.userservice.entity.SysErrorLog;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.exception.AutomaticScoringSystemException;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.DateUtil;
import com.fcy.userservice.util.ResultUtil;
import com.fcy.userservice.util.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @Describe: 统一异常处理类
 * @Author: fuchenyang
 * @Date: 2021/3/7 16:42
 */

@Slf4j
@Component
@ControllerAdvice
public class AutomaticScoringSystemExceptionAdvice {

    @Autowired
    private LogService logService;

    @ResponseBody
    @ExceptionHandler(AutomaticScoringSystemException.class)
    public Result<Object> automaticScoringSystemExceptionHandler(AutomaticScoringSystemException e) {
        log.error(e.getErrorMsg(), e);
        logService.logErr(e.getMessage());
        return ResultUtil.error(e.getErrorMsg());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        logService.logErr(e.getMessage());
        return ResultUtil.error(e.getMessage());
    }

}
