package com.fcy.logservice.mapper;

import com.fcy.logservice.entity.SysErrorLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/14 22:11
 */
@Mapper
public interface SysErrLogMapper {
    void save(SysErrorLog sysErrorLog);
}
