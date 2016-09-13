package com.github.schedulejob.config.datasource;

import com.github.schedulejob.common.AppConst;
import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 数据源配置
 *
 * @author: lvhao
 * @since: 2016-8-3 10:20
 */
public abstract class AbstractDataSourceConfig {

    /**
     * 属性前缀
     */
    protected static final String DB_DEFAULT_PREFIX = "datasource.default";
    protected static final String DB_READ_PREFIX = "datasource.read";
    protected static final String DB_WRITE_PREFIX = "datasource.write";


    // 数据源
    protected abstract DataSource defaultDataSource();
    protected abstract DataSource readDataSource();
    protected abstract DataSource writeDataSource();

    public Map<Object,Object> getDataSourceMap(){
        Map<Object,Object> dataMap = Maps.newHashMap();

        dataMap.put(AppConst.DbKey.DEFAULT, this.defaultDataSource());
        dataMap.put(AppConst.DbKey.READ, this.readDataSource());
        dataMap.put(AppConst.DbKey.WRITE, this.writeDataSource());

        DataSourceContextHolder.appendDbKey2Set(
                AppConst.DbKey.DEFAULT,
                AppConst.DbKey.READ,
                AppConst.DbKey.WRITE
        );
        return dataMap;
    }
}
