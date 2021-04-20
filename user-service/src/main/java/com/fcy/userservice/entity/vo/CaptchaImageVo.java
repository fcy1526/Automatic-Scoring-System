package com.fcy.userservice.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Describe: 验证码视图对象
 * @Author: fuchenyang
 * @Date: 2021/2/21 15:00
 */
@Data
@Builder
public class CaptchaImageVo {
    // 验证码图片base64码
    private String imageBase64;
    // 验证码加密值
    private String imageHashCode;
}
