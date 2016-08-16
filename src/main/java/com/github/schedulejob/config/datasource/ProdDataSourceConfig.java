package com.github.schedulejob.config.datasource;

import com.github.schedulejob.common.AppConst;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * 从配置文件中读取db prod配置
 *
 * @author: lvhao
 * @since: 2016-4-15 17:10
 */
@Profile(AppConst.Env.PROD)
@Configuration
public class ProdDataSourceConfig extends DataSourceConfig{

    ////////////////////////////////////////////////////
    //  prod 环境配置文件
    ////////////////////////////////////////////////////
    @Bean
    @ConfigurationProperties(
            value = PROD_CONF,
            prefix= DB_DEFAULT_PREFIX)
    @Override
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            value = PROD_CONF,
            prefix= DB_READ_PREFIX)
    @Override
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations=  {PROD_CONF},
            prefix= DB_WRITE_PREFIX)
    @Override
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
}
