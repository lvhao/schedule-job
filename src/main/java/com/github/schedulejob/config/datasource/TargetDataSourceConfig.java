package com.github.schedulejob.config.datasource;

import com.github.schedulejob.common.AppConst;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 从配置文件中读取db配置
 *
 * @author: lvhao
 * @since: 2016-4-15 17:10
 */
@Configuration
public class TargetDataSourceConfig {

    // 组装db到Map
    // 注册到 DynamicDataSource的targetDataSources属性
    @Bean
    public Map<Object,Object> getDataSourceMap() {
        Map<Object,Object> dataMap = new HashMap<>();

        dataMap.put(AppConst.DBType.DEFAULT, this.defaultDataSource());
        dataMap.put(AppConst.DBType.READ, this.readDataSource());
        dataMap.put(AppConst.DBType.WRITE, this.writeDataSource());

        DataSourceContextHolder.appendDbKey2Set(
                AppConst.DBType.DEFAULT,
                AppConst.DBType.READ,
                AppConst.DBType.WRITE
        );
        return dataMap;
    }

    // 从默认配置文件出读取db配置
    @Bean
    @ConfigurationProperties(locations="datasource.properties",prefix="default.datasource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(locations="datasource.properties",prefix="db_read.datasource")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(locations="datasource.properties",prefix="db_write.datasource")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }
}
