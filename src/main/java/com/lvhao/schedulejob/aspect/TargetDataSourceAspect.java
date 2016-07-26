package com.lvhao.schedulejob.aspect;


import com.lvhao.schedulejob.anno.TargetDataSource;
import com.lvhao.schedulejob.common.AppConst;
import com.lvhao.schedulejob.config.datasource.DataSourceContextHolder;
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

    @Around(
        value = "anyPublicMethod() && annotationOnClass(targetDataSource)",
        argNames = "proceedingJoinPoint,targetDataSource"
    )
    public Object methodInvoke(
            ProceedingJoinPoint proceedingJoinPoint,
            TargetDataSource targetDataSource) throws Throwable {
        checkNotNull(targetDataSource,"@TargetDataSource为空");
        String dbKey = null;
        MethodSignature ms = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = ms.getMethod();

        // 方法是否标注了注解 TargetDataSource
        if(method.isAnnotationPresent(TargetDataSource.class)){
            targetDataSource = method.getDeclaredAnnotationsByType(TargetDataSource.class)[0];
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
