package com.fcy.userservice.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import com.fcy.userservice.annotation.AuthCheck;
import com.fcy.userservice.enums.OperationEnum;
import com.fcy.userservice.entity.JWT;
import com.fcy.userservice.entity.SysLog;
import com.fcy.userservice.entity.User;
import com.fcy.userservice.entity.dto.UserLoginRequestDto;
import com.fcy.userservice.entity.vo.CaptchaImageVo;
import com.fcy.userservice.feign.AuthServiceClient;
import com.fcy.userservice.service.LogService;
import com.fcy.userservice.service.UserService;
import com.fcy.userservice.util.*;
import com.fcy.userservice.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.fcy.userservice.common.Constants.*;

/**
 * @Describe: 登录管理
 * @Author: fuchenyang
 * @Date: 2021/2/14 19:52
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LogService logService;

    @Autowired
    private AuthServiceClient authServiceClient;

    /**
     * 登录
     * @param userLoginRequestDto
     * @return
     */
    @PostMapping("")
    public Result<Object> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String captcha = userLoginRequestDto.getCaptcha();  // 验证码
        String imageHashCode = userLoginRequestDto.getImageHashCode();  // 验证码哈希值
        String code = redisTemplate.opsForValue().get(imageHashCode);   // 获取redis中的值
        Result res = null;
        User user = null;
        if ((!StringUtils.isEmpty(code)) && code.equals(captcha)) { // 验证码正确
            user = userService.findByUserid(userLoginRequestDto.getUserId());
            if (null != user && BPwdEncoderUtil.matches(userLoginRequestDto.getPassword(), user.getPassword())) { // 验证密码
                // 令牌
                JWT jwt = authServiceClient.getToken(AUTHORIZATION, GRANT_TYPE,
                        userLoginRequestDto.getUserId(), userLoginRequestDto.getPassword());
                // 记录日志
                SysLog sysLog = SysLog.builder()
                        .sysUserId(user.getUserId())
                        .sysTrueName(user.getTrueName())
                        .opertime(DateUtil.getCurrentDate())
                        .opertype(OperationEnum.LOGIN)
                        .opertable(SystemTable.T_USERINFO)
                        .operation("login system")
                        .build();
                logService.log(sysLog);
                // 返回令牌
                res = ResultUtil.success(jwt);
            } else res = ResultUtil.error("账号或密码错误");
        } else {
            res = ResultUtil.error("验证码错误");
        }
        return res;
    }

    /**
     * 生成验证码图片
     * @return
     */
    @GetMapping("captcha")
    public CaptchaImageVo getCaptcha() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(125, 40); // 设置验证码大小
        String code = captcha.getCode(); // 获取验证码字符串
        String codeHash = HashCodeUtil.hashKeyForDisk(code); // 对验证码进行加密
        codeHash += Math.random(); // 通过随机值加盐，保证验证码哈希值是由后端产生的
        // 写入验证码
        redisTemplate.opsForValue().set(codeHash, code); // 加密后的哈希字符串作为键，验证码字符串作为值
        CaptchaImageVo imageVo = CaptchaImageVo.builder()
                .imageBase64(captcha.getImageBase64()) // 将图片转换为base64位字符串
                .imageHashCode(codeHash)    // 和验证码哈希值一同返回前端
                .build();
        return imageVo;
    }
}
