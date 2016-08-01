package com.github.schedulejob.config.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.github.schedulejob.common.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * fastjson converter
 *
 * @author: lvhao
 * @since: 2016-7-29 15:42
 */
@Configuration
public class FastJsonHttpMessageConverterConfig {

    @Bean
    public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter4(){
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

        // filter
        // 当class不为null时，针对特定类型；当class为null时，针对所有类型。
        // Response 不赋值page时 不进行序列化
        Predicate<Response> excludeIfNull = resp -> Objects.nonNull(resp.getPage());
        CustomPropertyPreFilter customPropertyPreFilter = new CustomPropertyPreFilter(Response.class,excludeIfNull);
        customPropertyPreFilter.setMaxLevel(1);
        customPropertyPreFilter.getExcludes().add("page");
        fastJsonConfig.setSerializeFilters(customPropertyPreFilter);
        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter4;
    }
}
