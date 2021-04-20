package com.fcy.scoreservice.service;

import com.fcy.scoreservice.entity.SysErrorLog;
import com.fcy.scoreservice.entity.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.Map;

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
