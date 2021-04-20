package com.fcy.scoreservice.mapper;

import com.fcy.scoreservice.entity.SysErrorLog;
import com.fcy.scoreservice.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Describe:
 * @Author: fuchenyang
 * @Date: 2021/4/19 21:51
 */
@Mapper
public interface LogMapper {

    List<SysLog> getSysLog(Map map);

    List<SysErrorLog> getSysErrLog();
}
