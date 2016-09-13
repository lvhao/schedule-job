package com.github.schedulejob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * path路径配置
 *
 * @author: lvhao
 * @since: 2016-9-12 19:18
 */
@Configuration
public class PathCfg {

    @Value("${cfg.quartz.path}")
    private String quartzCfg;

    @Value("${cfg.redis.path}")
    private String redisCfg;

    @Value("${cfg.db.path}")
    private String dbCfg;

    public String getQuartzCfg() {
        return quartzCfg;
    }

    public String getRedisCfg() {
        return redisCfg;
    }

    public String getDbCfg() {
        return dbCfg;
    }
}
