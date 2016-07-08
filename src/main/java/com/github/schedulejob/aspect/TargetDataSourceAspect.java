package com.github.schedulejob.aspect;


import com.github.schedulejob.anno.TargetDataSource;
import com.github.schedulejob.common.AppConst;
import com.github.schedulejob.config.datasource.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 拦截所有service包下的类
 * 执行 @TargetDataSource 检查
 *
 * @author: lvhao
 * @since: 2016-4-15 11:32
 */
@Component
@Aspect
public class TargetDataSourceAspect {

    @Around("execution(* com.github.schedulejob.service.*.*(..))")
    public Object methodInvoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TargetDataSource targetDataSource = null;
        String dbKey = null;
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
        MethodSignature ms = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = ms.getMethod();

        // 类上是否标注了注解
        if(clazz.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = clazz.getAnnotationsByType(TargetDataSource.class)[0];
        }

        // 标注了注解 TargetDataSource
        if(method.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = method.getDeclaredAnnotationsByType(TargetDataSource.class)[0];
        }
        dbKey = targetDataSource.value();
        if (!StringUtils.hasText(dbKey)) {
            dbKey = AppConst.DBType.DEFAULT;
        }
        DataSourceContextHolder.initDbContext(dbKey);
        Object result = proceedingJoinPoint.proceed();
        DataSourceContextHolder.destroyDbContext();
        return result;
    }
}
