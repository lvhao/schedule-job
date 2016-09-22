package com.github.schedulejob.config;

import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 扩展路径配置
 *
 * @author: lvhao
 * @since: 2016-9-14 17:48
 */
@Configuration
public class ExternalPathConfig {

    @Value("${config.quartz-file-name}")
    private String quartzConfigFileName;

    @Value("${config.redis-file-name}")
    private String redisConfigFileName;

    @Value("${config.db-file-name}")
    private String dbConfigFileName;

    @Autowired
    private ConfigurableEnvironment environment;

    @Bean
    public Map<String,Map<String,String>> externalProperties(){
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        Map<String,Map<String,String>> result = Maps.newHashMap();
        mutablePropertySources.forEach(mps -> {
            String pathName = mps.getName();
            String keyName = null;
            if (pathName.contains(quartzConfigFileName)) {
                keyName = quartzConfigFileName;
            } else if(pathName.contains(dbConfigFileName)) {
                keyName = dbConfigFileName;
            } else if(pathName.contains(redisConfigFileName)){
                keyName = redisConfigFileName;
            }

            if (Objects.nonNull(keyName)) {
                result.put(keyName,(Map<String, String>) mps.getSource());
            }
        });
        return result;
    }

    @Bean
    public Properties quartzCfg(){
        Map<String,String> map = externalProperties().get(quartzConfigFileName);
        final Properties prop = new Properties();
        if (Objects.nonNull(map)) {
            map.forEach((k,v) -> prop.setProperty(k,v));
        }
        return prop;
    }
}
