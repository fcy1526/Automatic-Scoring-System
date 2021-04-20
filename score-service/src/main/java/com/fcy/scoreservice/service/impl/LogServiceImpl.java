package com.fcy.scoreservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.fcy.scoreservice.common.Constants;
import com.fcy.scoreservice.config.RabbitConfig;
import com.fcy.scoreservice.entity.Log;
import com.fcy.scoreservice.entity.SysErrorLog;
import com.fcy.scoreservice.entity.SysLog;
import com.fcy.scoreservice.entity.User;
import com.fcy.scoreservice.entity.dto.LogDto;
import com.fcy.scoreservice.mapper.LogMapper;
import com.fcy.scoreservice.mapper.UserMapper;
import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.util.AuthUtil;
import com.fcy.scoreservice.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/12 20:24
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogMapper logMapper;

    @Override
    public void log(SysLog sysLog) {
        LogDto logDto = LogDto.builder()
                .type(Constants.SYS_LOG)
                .log(sysLog)
                .build();
        amqpTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(logDto));
    }

    @Override
    public void logBuild(SysLog sysLog) {
        // 填充当前用户信息 当前时间
        buildUserinfo(sysLog);
        log(sysLog);
    }

    @Override
    public void logErr(String errMsg) {
        SysErrorLog sysErrorLog = SysErrorLog.builder().message(errMsg).build();
        buildUserinfo(sysErrorLog);
        LogDto logDto = LogDto.builder()
                .type(Constants.SYS_ERR_LOG)
                .log(sysErrorLog)
                .build();
        amqpTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(logDto));
    }

    @Override
    public PageInfo<SysLog> getSysLog(Map map) {
        setPage(map);
        return new PageInfo<SysLog>(logMapper.getSysLog(map));
    }

    @Override
    public PageInfo<SysErrorLog> getSysErrLog(Map map) {
        setPage(map);
        return new PageInfo<SysErrorLog>(logMapper.getSysErrLog());
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

    /**
     * 填充当前用户信息 当前时间
     * @param log
     * @return
     */
    public Log buildUserinfo(Log log) {
        User currentUser = userMapper.findByUserid(AuthUtil.getCurrentUserid());
        log.setSysUserId(currentUser.getUserId());
        log.setSysTrueName(currentUser.getTrueName());
        log.setOpertime(DateUtil.getCurrentDate());
        return log;
    }
}
