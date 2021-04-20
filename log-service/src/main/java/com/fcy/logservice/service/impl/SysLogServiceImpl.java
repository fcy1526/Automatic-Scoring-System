package com.fcy.logservice.service.impl;

import com.fcy.logservice.entity.SysErrorLog;
import com.fcy.logservice.entity.SysLog;
import com.fcy.logservice.mapper.SysErrLogMapper;
import com.fcy.logservice.mapper.SysLogMapper;
import com.fcy.logservice.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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


    @Override
    public PageInfo<SysLog> getSysLog(Map map) {
        setPage(map);
        return new PageInfo<SysLog>(sysLogMapper.getSysLog(map));
    }

    @Override
    public PageInfo<SysErrorLog> getSysErrLog(Map map) {
        setPage(map);
        return new PageInfo<SysErrorLog>(sysErrLogMapper.getSysErrLog());
    }

    /**
     * 设置分页
     * @param map
     * @return
     */
    public void setPage(Map map) {
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");
        // 设置分页
        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }
}
