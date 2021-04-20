package com.fcy.scoreservice.annotation;

import com.fcy.scoreservice.exception.AutomaticScoringSystemException;
import com.fcy.scoreservice.service.AuthService;
import com.fcy.scoreservice.util.AuthUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Describe: 执行方法前验证用户权限
 * @Author: fuchenyang 
 * @Date: 2021/3/27 21:48
 */
@Aspect
@Component
public class AuthAspect {

    @Autowired
    private AuthService authService;

    @Pointcut("@annotation(com.fcy.scoreservice.annotation.AuthCheck)")
    public void roleCheck() {
    }

    /**
     * 前置增强：验证当前用户是否有对应接口的权限
     * @param authCheck
     */
    @Before(value = "roleCheck() && @annotation(authCheck)")
    public void before(AuthCheck authCheck) {
//        System.out.println("authCheck = " + authCheck.btnName());
        // 获取当前用户id
        String userId = AuthUtil.getCurrentUserid();
        // 验证用户权限
        String authId = authService.checkAuth(userId, authCheck.btnName());
        if (StringUtils.isEmpty(authId)) {
            // 验证失败，用户没有该权限
            throw new AutomaticScoringSystemException(HttpStatus.UNAUTHORIZED, "权限不足");
        }
    }

//    @Around("@annotation(AuthCheck)")
//    public Object around(ProceedingJoinPoint joinPoint) {
//        System.out.println("环绕前");
//        Object obj = null;
//        try {
//            obj = joinPoint.proceed();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        System.out.println("环绕后");
//        return obj;
//    }
}
