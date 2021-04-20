package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.SysLog;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/12 20:23
 */
public interface LogService {

    void log(SysLog sysLog);

    void logBuild(SysLog sysLog);

    void logErr(String errMsg);

}
