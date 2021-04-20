package com.fcy.userservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.fcy.userservice.common.Constants;
import com.fcy.userservice.entity.Log;
import com.fcy.userservice.entity.SysErrorLog;
import com.fcy.userservice.config.RabbitConfig;
import com.fcy.userservice.entity.SysLog;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.LogDto;
import com.fcy.userservice.mapper.UserMapper;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.util.AuthUtil;
import com.fcy.userservice.util.DateUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
