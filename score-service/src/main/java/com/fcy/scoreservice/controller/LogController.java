package com.fcy.scoreservice.controller;

import com.fcy.scoreservice.service.LogService;
import com.fcy.scoreservice.util.ResultUtil;
import com.fcy.scoreservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/4/19 21:44
 */
@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("sysLog")
    public Result<Object> getSysLog(@RequestParam("type") String type,
                                    @RequestParam("pagenum") String pageNum,
                                    @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("type", type);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(logService.getSysLog(map));
    }

    @GetMapping("sysErrLog")
    public Result<Object> getSysLog(@RequestParam("pagenum") String pageNum,
                                    @RequestParam("pagesize") String pageSize) {
        Map map = new HashMap();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return ResultUtil.success(logService.getSysErrLog(map));
    }
}
