package com.lvhao.schedulejob.config.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis 资源文件加载路径
 *
 * @author: lvhao
 * @since: 2016-7-22 14:08
 */
@ConfigurationProperties(locations = "mybatis.properties",prefix="mybatis.location")
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
}
