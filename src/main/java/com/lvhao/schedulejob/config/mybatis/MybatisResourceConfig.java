package com.lvhao.schedulejob.config.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 资源文件加载路径
 *
 * @author: lvhao
 * @since: 2016-7-22 14:08
 */
@Configuration
@ConfigurationProperties(
        locations = "mybatis.properties",
        ignoreUnknownFields = false,
        prefix="mybatis.location"
)
@EnableConfigurationProperties(MybatisResourceConfig.class)
public class MybatisResourceConfig {

    private String config;
    private String xmlMapper;
    private String mapper;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getXmlMapper() {
        return xmlMapper;
    }

    public void setXmlMapper(String xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MybatisResourceConfig{");
        sb.append("config='").append(config).append('\'');
        sb.append(", xmlMapper='").append(xmlMapper).append('\'');
        sb.append(", mapper='").append(mapper).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
