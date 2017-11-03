package com.github.schedulejob.aspect;


import com.github.schedulejob.anno.TargetDataSource;
import com.github.schedulejob.common.AppConst;
import com.github.schedulejob.config.datasource.DataSourceContextHolder;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;


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

    @Around(value = "anyPublicServiceMethod() && annotationOnClass(targetDataSource))", argNames = "proceedingJoinPoint,targetDataSource")
    public Object annotationOnClassInvoke(ProceedingJoinPoint proceedingJoinPoint, TargetDataSource targetDataSource) throws Throwable {
        MethodSignature ms = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = ms.getMethod();

        // 方法是否标注了注解 TargetDataSource
        if(method.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = method.getAnnotationsByType(TargetDataSource.class)[0];
        }

        return doInvoke(proceedingJoinPoint, targetDataSource);
    }


    /**
     * 任何标记了注解的类
     */
    @Pointcut(value = "@within(targetDataSource)", argNames = "targetDataSource")
    public void annotationOnClass(TargetDataSource targetDataSource) {
    }


    /**
     * 任何标记了注解的方法
     */
    @Pointcut(value = "@annotation(targetDataSource)", argNames = "targetDataSource")
    public void annotationOnMethod(TargetDataSource targetDataSource) {
    }


    /**
     * 由于aspect 表达式的限制，分成两个切面处理
     */
    private Object doInvoke(ProceedingJoinPoint proceedingJoinPoint, TargetDataSource targetDataSource) throws Throwable {
        String dbKey = Objects.isNull(targetDataSource) ? AppConst.DbKey.DEFAULT : targetDataSource.value();

        DataSourceContextHolder.initDbContext(dbKey);
        Object result = proceedingJoinPoint.proceed();
        DataSourceContextHolder.destroyDbContext();
        return result;
    }


    @Around(value = "anyPublicServiceMethod() && annotationOnMethod(targetDataSource))", argNames = "proceedingJoinPoint,targetDataSource")
    public Object annotationOnMethodInvoke(ProceedingJoinPoint proceedingJoinPoint, TargetDataSource targetDataSource) throws Throwable {
        return doInvoke(proceedingJoinPoint, targetDataSource);
    }


    /**
     * 指定到包下 此处接口类里默认添加了注解{@link TargetDataSource}
     *
     * 任何public service方法
     */
    @Pointcut(value = "execution(* com.github.schedulejob.service.DefaultDataSourceService+.* (..))")
    public void anyPublicServiceMethod() {
    }
}
