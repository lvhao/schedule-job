package com.github.schedulejob.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 从配置文件中读取db dev配置
 *
 * @author: lvhao
 * @since: 2016-4-15 17:10
 */
@Configuration
public class DataSourceConfig extends AbstractDataSourceConfig {


    ////////////////////////////////////////////////////
    //  dev 环境配置文件
    ////////////////////////////////////////////////////
    /**
     * 从默认配置文件出读取db配置
     * 此处需要写上classpath 否则无法找到资源 导致绑定失败
     * 具体查看如下方法
     * {@link org.springframework.core.io.DefaultResourceLoader#getResource}
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix= DB_DEFAULT_PREFIX)
    @Override
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix= DB_READ_PREFIX)
    @Override
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix= DB_WRITE_PREFIX)
    @Override
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
}
