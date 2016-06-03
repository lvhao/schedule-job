package com.github.schedulejob.config.datasource;

import com.github.schedulejob.common.APPConst;
import com.github.schedulejob.common.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 实现抽象类 AbstractRoutingDataSource
 * 动态获取datasource
 *
 * @author: lvhao
 * @since: 2016-4-15 17:35
 */

// 由于有多个DataSource实例，
// 必须标注一个默认绑定，
// 否则对于DataSource类型自动绑定时出错
@Primary
@Configuration
public class DynamicDatasource extends AbstractRoutingDataSource {

    @Autowired
    private TargetDataSourceConfig targetDataSourceConfig;

    @PostConstruct
    private void initDataSourceMap() {
        Map<Object,Object> dataSourceMap = targetDataSourceConfig.getDataSourceMap();
        setTargetDataSources(dataSourceMap);
        setDefaultTargetDataSource(dataSourceMap.get(APPConst.DBType.DEFAULT));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getCurrentDbKey();
    }
}
