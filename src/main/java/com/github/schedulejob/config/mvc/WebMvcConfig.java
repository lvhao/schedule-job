package com.github.schedulejob.config.mvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.github.schedulejob.interceptor.RequestHandleTimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截器配置
 *
 * @author: lvhao
 * @since: 2016-4-18 13:29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4 =new FastJsonHttpMessageConverter4();
        fastJsonHttpMessageConverter4.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        // 默认utf-8
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.SkipTransientField,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue
        );
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter4);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHandleTimeInterceptor());
    }
}
