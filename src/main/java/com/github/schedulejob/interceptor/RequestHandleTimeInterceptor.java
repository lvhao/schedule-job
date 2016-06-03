package com.github.schedulejob.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册mvc interceptor
 *
 * @author: lvhao
 * @since: 2016-4-18 13:26
 */
public class RequestHandleTimeInterceptor extends HandlerInterceptorAdapter {
    private static final String TIMESTAMP_KEY = "_TS";
    private static final Logger log = LoggerFactory.getLogger(RequestHandleTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(TIMESTAMP_KEY,System.nanoTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long _ts = Long.parseLong(request.getAttribute(TIMESTAMP_KEY).toString());
        request.removeAttribute(TIMESTAMP_KEY);
        log.info("REQUEST_HANDLE_TIME={}ms",(System.nanoTime() - _ts)/(1000*1000));
    }
}
