package com.fcy.logservice.service;

import com.fcy.logservice.entity.SysErrorLog;
import com.fcy.logservice.entity.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/12 19:02
 */
public interface SysLogService {

    void saveSysLog(SysLog sysLog);

    void saveSysErrLog(SysErrorLog sysErrorLog);

    PageInfo<SysLog> getSysLog(Map map);

    PageInfo<SysErrorLog> getSysErrLog(Map map);
}
