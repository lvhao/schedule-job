package com.github.schedulejob.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 耗时统计 interceptor
 *
 * @author: lvhao
 * @since: 2016-4-18 13:26
 */
public class RequestHandleTimeInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(RequestHandleTimeInterceptor.class);
    private static final ThreadLocal<Long> consumingTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        consumingTime.set(System.nanoTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long st = consumingTime.get();
        consumingTime.remove();
        log.info("REQUEST_HANDLE_TIME={}ms",(System.nanoTime() - st)/(1000*1000));
    }
}
