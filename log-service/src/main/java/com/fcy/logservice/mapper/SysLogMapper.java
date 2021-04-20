package com.fcy.logservice.mapper;

import com.fcy.logservice.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/13 11:22
 */
@Mapper
public interface SysLogMapper {
    void save(SysLog sysLog);

    List<SysLog> getSysLog(Map map);
}
