package com.fcy.logservice.util;


import com.fcy.logservice.util.dto.Result;
import com.fcy.logservice.util.enums.ResponseStatusEnum;

/**
 * @Describe: 响应工具类
 * @Author: fuchenyang
 * @Date: 2021/2/10 15:30
 */
public class ResultUtil {

    private ResultUtil() {

    }

    /**
     * 请求成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> res = new Result<T>();
        res.setReturnCode(ResponseStatusEnum.SUCCESS.getReturnCode());
        res.setReturnMsg(ResponseStatusEnum.SUCCESS.getReturnMsg());
        res.setData(data);
        return res;
    }

    /**
     * 请求成功
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> res = new Result<T>();
        res.setReturnCode(ResponseStatusEnum.SUCCESS.getReturnCode());
        res.setReturnMsg(ResponseStatusEnum.SUCCESS.getReturnMsg());
        return res;
    }

    /**
     * 请求失败
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String msg) {
        Result<T> res = new Result<T>();
        res.setReturnCode(ResponseStatusEnum.FAILURE.getReturnCode());
        res.setReturnMsg(msg);
        return res;
    }

    /**
     * 请求失败
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> res = new Result<T>();
        res.setReturnCode(code);
        res.setReturnMsg(msg);
        return res;
    }

    /**
     * 请求失败
     * @param <T>
     * @return
     */
    public static <T> Result<T> error() {
        Result<T> res = new Result<T>();
        res.setReturnCode(ResponseStatusEnum.FAILURE.getReturnCode());
        res.setReturnMsg(ResponseStatusEnum.FAILURE.getReturnMsg());
        return res;
    }
}
