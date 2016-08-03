package com.github.schedulejob.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Jackson 配置
 *
 * Created by root on 2016/8/2 0002.
 */
@Configuration
public class JacksonHttpMessageConverterConfig {

    @Bean
    public ObjectMapper objectMapper(){
        Jackson2ObjectMapperBuilder j2omb = new Jackson2ObjectMapperBuilder();
        return j2omb.autoDetectFields(true)
                .autoDetectGettersSetters(true)
                .createXmlMapper(false)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false)
                .featuresToEnable(
                        JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, // 无引号json 非json规约 默认false
                        JsonParser.Feature.ALLOW_SINGLE_QUOTES, // 单引号json

                        JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, // bigdecimal.toPlainString

                        SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, // ['a','b','c'] not "abc"

                        DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, // unknown enum -> null
                        DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, // [] -> null
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT // "" -> null
                )
                .build();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter mj2mc = new MappingJackson2HttpMessageConverter();
        mj2mc.setDefaultCharset(Charset.forName("utf-8"));
        mj2mc.setPrettyPrint(false);
        mj2mc.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        ObjectMapper om = this.objectMapper();
        mj2mc.setObjectMapper(om);
        return mj2mc;
    }
}
