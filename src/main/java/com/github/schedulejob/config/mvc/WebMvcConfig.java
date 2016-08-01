package com.github.schedulejob.config.mvc;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.github.schedulejob.interceptor.RequestHandleTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 拦截器配置
 *
 * @author: lvhao
 * @since: 2016-4-18 13:29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter4);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHandleTimeInterceptor());
    }
}
