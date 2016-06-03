package com.github.schedulejob.config.quartz;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.text.ParseException;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-3 17:36
 */
public class CustomCronTriggerFactoryBean extends CronTriggerFactoryBean {
    @Override
    public void afterPropertiesSet() throws ParseException {
        super.afterPropertiesSet();
        getJobDataMap().remove("jobDetail");
    }
}
