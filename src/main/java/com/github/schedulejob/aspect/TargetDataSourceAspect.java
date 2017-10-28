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
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

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

    /**
     * 任何public方法
     */
    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {}

    /**
     * 任何标记了注解的类
     * @param targetDataSource
     */
    @Pointcut(value = "@within(targetDataSource)", argNames = "targetDataSource")
    public void annotationOnClass(TargetDataSource targetDataSource){}

    /**
     * 任何标记了注解的方法
     * @param targetDataSource
     */
    @Pointcut(value = "@annotation(targetDataSource)", argNames = "targetDataSource")
    public void annotationOnMethod(TargetDataSource targetDataSource){}

    @Around(
        value = "anyPublicMethod() && (annotationOnMethod(targetDataSource) || annotationOnClass(targetDataSource))",
        argNames = "proceedingJoinPoint,targetDataSource"
    )
    public Object methodInvoke(
            ProceedingJoinPoint proceedingJoinPoint,
            TargetDataSource targetDataSource) throws Throwable {
        checkNotNull(targetDataSource,"@TargetDataSource为空");
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();

        // 类上是否标注了注解
        if(clazz.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = clazz.getAnnotationsByType(TargetDataSource.class)[0];
        }

        String dbKey;
        MethodSignature ms = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = ms.getMethod();

        // 方法是否标注了注解 TargetDataSource
        if(method.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = method.getAnnotationsByType(TargetDataSource.class)[0];
        }

        dbKey = targetDataSource.value();
        if (!StringUtils.hasText(dbKey)) {
            dbKey = AppConst.DbKey.DEFAULT;
        }
        DataSourceContextHolder.initDbContext(dbKey);
        Object result = proceedingJoinPoint.proceed();
        DataSourceContextHolder.destroyDbContext();
        return result;
    }
}
