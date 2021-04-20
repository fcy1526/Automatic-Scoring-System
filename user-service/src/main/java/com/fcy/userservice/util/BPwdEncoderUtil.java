package com.fcy.userservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Describe: BCrypt密码验证类
 * @Author: fuchenyang
 * @Date: 2021/3/1 21:41
 */
public class BPwdEncoderUtil {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    // 加密密码
    public static String BCryptPassword(String password) {
        return ENCODER.encode(password);
    }

    // 验证密码是否正确
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
