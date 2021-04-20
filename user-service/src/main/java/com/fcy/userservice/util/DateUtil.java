package com.fcy.userservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fcy.userservice.common.Constants.DATE_FORMAT;

/**
 * @Describe: 时间工具类
 * @Author: fuchenyang
 * @Date: 2021/3/12 20:56
 */
public class DateUtil {

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date());
    }
}
