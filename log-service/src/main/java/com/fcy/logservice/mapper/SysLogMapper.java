package com.fcy.logservice.mapper;

import com.fcy.logservice.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/13 11:22
 */
@Mapper
public interface SysLogMapper {
    void save(SysLog sysLog);
}
