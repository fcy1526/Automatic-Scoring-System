package com.fcy.logservice.mapper;

import com.fcy.logservice.entity.SysErrorLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/3/14 22:11
 */
@Mapper
public interface SysErrLogMapper {
    void save(SysErrorLog sysErrorLog);

    List<SysErrorLog> getSysErrLog();
}
