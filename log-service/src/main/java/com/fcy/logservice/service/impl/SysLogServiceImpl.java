package com.fcy.logservice.service.impl;

import com.fcy.logservice.entity.SysErrorLog;
import com.fcy.logservice.entity.SysLog;
import com.fcy.logservice.mapper.SysErrLogMapper;
import com.fcy.logservice.mapper.SysLogMapper;
import com.fcy.logservice.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Describe: 
 * @Author: fuchenyang 
 * @Date: 2021/3/12 19:13
 */
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysErrLogMapper sysErrLogMapper;

    @Override
    public void saveSysLog(SysLog sysLog) {
        sysLogMapper.save(sysLog);
//        System.out.println("syslog = " + sysLog.toString());
    }

    @Override
    public void saveSysErrLog(SysErrorLog sysErrorLog) {
        sysErrLogMapper.save(sysErrorLog);
    }
}
