package com.fcy.scoreservice.exception.advice;

import com.fcy.scoreservice.exception.AutomaticScoringSystemException;
import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.util.ResultUtil;
import com.fcy.scoreservice.util.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
