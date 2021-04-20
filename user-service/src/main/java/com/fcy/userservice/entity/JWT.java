package com.fcy.userservice.entity;

import lombok.Data;

/**
 * @Describe: JWT
 * @Author: fuchenyang
 * @Date: 2021/2/24 19:52
 */
@Data
public class JWT {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private int expires_in;

    private String scope;

    private String jti;

}
