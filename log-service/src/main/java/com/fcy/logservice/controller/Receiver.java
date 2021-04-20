package com.fcy.logservice.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcy.logservice.common.Constants;
import com.fcy.logservice.entity.SysErrorLog;
import com.fcy.logservice.entity.SysLog;
import com.fcy.logservice.entity.dto.LogDto;
import com.fcy.logservice.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @Describe: 接收rabbitMQ服务器信息
 * @Author: fuchenyang
 * @Date: 2021/3/12 19:01
 */
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private SysLogService sysLogService;

    public void receiveMessage(String message) {
//        System.out.println("Received <" + message + ">");
        LogDto log = JSON.parseObject(message, LogDto.class);
        ObjectMapper objectMapper = new ObjectMapper();
        if (log.getType().equalsIgnoreCase(Constants.SYS)) {
            // 系统日志
            SysLog sysLog = objectMapper.convertValue(log.getLog(), SysLog.class);
            sysLogService.saveSysLog(sysLog);
        } else if (log.getType().equalsIgnoreCase(Constants.ERR)){
            // 异常日志
            SysErrorLog sysErrorLog = objectMapper.convertValue(log.getLog(), SysErrorLog.class);
            sysLogService.saveSysErrLog(sysErrorLog);
        }
        latch.countDown();
    }
}
