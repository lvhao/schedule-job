package com.github.schedulejob.config.datasource;

import com.github.schedulejob.common.AppConst;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 从配置文件中读取db配置
 *
 * @author: lvhao
 * @since: 2016-4-15 17:10
 */
@Configuration
public class TargetDataSourceConfig {

    @Autowired
    private Environment environment;

    /**
     * profile 配置
     */
    private static final Set<String> DB_PROFILE_KEYS = new HashSet<String>(){
        {
            add(AppConst.DbProfile.DEFAULT);
            add(AppConst.DbProfile.TEST);
            add(AppConst.DbProfile.PROD);
        }
    };

    /**
     * 配置路径
     */
    private static final String DEV_CONF = "classpath:config/datasource.yml";
    private static final String TEST_CONF = "file:datasource.yml";
    private static final String PROD_CONF = "file:datasource.yml";

    /**
     * 属性前缀
     */
    private static final String DB_DEFAULT_PREFIX = "datasource.default";
    private static final String DB_READ_PREFIX = "datasource.read";
    private static final String DB_WRITE_PREFIX = "datasource.write";

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
    @ConfigurationProperties(
            locations= {DEV_CONF},
            prefix= DB_DEFAULT_PREFIX)
    public DataSource devDefaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations= {DEV_CONF},
            prefix= DB_READ_PREFIX)
    public DataSource devReadDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations= {DEV_CONF},
            prefix= DB_WRITE_PREFIX)
    public DataSource devWriteDataSource() {
        return DataSourceBuilder.create().build();
    }

    ////////////////////////////////////////////////////
    //  test 环境配置文件
    ////////////////////////////////////////////////////
    @Bean
    @ConfigurationProperties(
            locations= {TEST_CONF},
            prefix= DB_DEFAULT_PREFIX)
    public DataSource testDefaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations= {TEST_CONF},
            prefix= DB_READ_PREFIX)
    public DataSource testReadDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations= {TEST_CONF},
            prefix= DB_WRITE_PREFIX)
    public DataSource testWriteDataSource() {
        return DataSourceBuilder.create().build();
    }

    ////////////////////////////////////////////////////
    //  prod 环境配置文件
    ////////////////////////////////////////////////////
    @Bean
    @ConfigurationProperties(
            locations= {PROD_CONF},
            prefix= DB_DEFAULT_PREFIX)
    public DataSource prodDefaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations= {PROD_CONF},
            prefix= DB_READ_PREFIX)
    public DataSource prodReadDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(
            locations=  {PROD_CONF},
            prefix= DB_WRITE_PREFIX)
    public DataSource prodWriteDataSource() {
        return DataSourceBuilder.create().build();
    }

    ////////////////////////////////////////////////////
    //  targetDataSources 组装
    ////////////////////////////////////////////////////

    public Map<Object,Object> getDbMapByEnv(String profile){
        Map<Object,Object> contextDbMap = Maps.newHashMap();
        Object defaultDataSource = null;
        Object readDataSource = null;
        Object writeDataSource = null;

        if (AppConst.DbProfile.DEFAULT.equals(profile)) {
            defaultDataSource = this.devDefaultDataSource();
            readDataSource = this.devReadDataSource();
            writeDataSource = this.devWriteDataSource();
        } else if(AppConst.DbProfile.TEST.equals(profile)){
            defaultDataSource = this.testDefaultDataSource();
            readDataSource = this.testReadDataSource();
            writeDataSource = this.testWriteDataSource();
        } else if(AppConst.DbProfile.PROD.equals(profile)){
            defaultDataSource = this.prodDefaultDataSource();
            readDataSource = this.prodReadDataSource();
            writeDataSource = this.prodWriteDataSource();
        }
        contextDbMap.put(AppConst.DbKey.DEFAULT, defaultDataSource);
        contextDbMap.put(AppConst.DbKey.READ, readDataSource);
        contextDbMap.put(AppConst.DbKey.WRITE, writeDataSource);
        return contextDbMap;
    }

    // 组装db到Map
    // 注册到 DynamicDataSource的targetDataSources属性
    @PostConstruct
    public Map<Object,Object> getDataSourceMap() {

        // 默认激活default
        String[] profiles = environment.getActiveProfiles();
        Map<Object,Object> dataMap = Maps.newHashMap();
        String profile = Arrays.stream(profiles)
                .filter(DB_PROFILE_KEYS::contains)
                .findFirst()
                .orElse(AppConst.DbProfile.DEFAULT);
        dataMap.putAll(getDbMapByEnv(profile));
        DataSourceContextHolder.appendDbKey2Set(
                AppConst.DbKey.DEFAULT,
                AppConst.DbKey.READ,
                AppConst.DbKey.WRITE
        );
        return dataMap;
    }
}
