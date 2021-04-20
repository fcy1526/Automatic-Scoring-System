package com.fcy.userservice.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Describe: 获取用户列表请求Dto
 * @Author: fuchenyang
 * @Date: 2021/3/5 0:46
 */
@Data
public class UserGetListRequestDto implements Serializable {

    private static final long serialVersionUID = 2613319461565723038L;

    /**
     * 查询输入框内容
     */
    private String queryInput;
    /**
     * 搜索类型
     */
    private String searchType;

}
