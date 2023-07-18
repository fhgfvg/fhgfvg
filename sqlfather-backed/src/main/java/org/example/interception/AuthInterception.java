package org.example.interception;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.common.UserHolder;
import io.common.annotation.AuthCheck;
import io.common.enums.ErrorCode;
import io.common.exceptions.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.entity.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验Aop
 */
@Aspect
@Component
public class AuthInterception {

    /**
     * 执行拦截
     * @param joinPoint
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object doInterception(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
//        过滤出非空白值
        List<String> list = Arrays.stream(authCheck.anyRole())
                .filter(StrUtil::isNotBlank).collect(Collectors.toList());
        String mustRole = authCheck.mustRole();
        User user = (User)UserHolder.getUser();

//        用户角色
        String userrole = user.getUserrole();

//        拥有任一角色即可放行
        if(CollectionUtil.isNotEmpty(list)){
            if(!(list.contains(userrole)))
                throw new CustomException(ErrorCode.NO_AUTH_ERROR);
        }

//        是该角色才可放行
        if(StrUtil.isNotEmpty(mustRole)){
            if(!(mustRole.equals(userrole)))
                throw new CustomException(ErrorCode.NO_AUTH_ERROR);
        }
//        放行
        return joinPoint.proceed();

    }
}
