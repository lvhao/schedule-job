package com.github.schedulejob.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.ConfigurableWebEnvironment;

import java.util.Properties;

/**
 * 扩展路径配置
 *
 * @author: lvhao
 * @since: 2016-9-14 17:48
 */
@Configuration
public class ExternalPathConfig {

    private static final String CONFIG_PATH_KEY = "config.path";
    private static final String CFG_FILENAME_QUARTZ_KEY = "config.filename.quartz";

    private static class PathCfg {
        private String path;
        private String redisFileName;
        private String quartzFileName;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRedisFileName() {
            return redisFileName;
        }

        public void setRedisFileName(String redisFileName) {
            this.redisFileName = redisFileName;
        }

        public String getQuartzFileName() {
            return quartzFileName;
        }

        public void setQuartzFileName(String quartzFileName) {
            this.quartzFileName = quartzFileName;
        }
    }

    @Autowired
    private ConfigurableWebEnvironment environment;

    @Bean
    @ConfigurationProperties("config")
    public PathCfg customResourceCfg(){
        return new PathCfg();
    }

    @Bean
    public Properties quartzProperties(){
        String configPath = customResourceCfg().getPath();
        String configFileName = customResourceCfg().getQuartzFileName();
        MutablePropertySources mps = environment.getPropertySources();
        PropertySource ps = mps.get(configPath+configFileName);
//        MapPropertySource xx = mps.get(configPath+configFileName);

        return null;
    }


}
