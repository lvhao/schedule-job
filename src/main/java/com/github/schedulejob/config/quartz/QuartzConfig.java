package com.github.schedulejob.config.quartz;

import com.github.schedulejob.schedule.QuartzTestJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-2 20:09
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private QuartzTestJob quartzTestJob;

    @Autowired
    private DataSource dataSource;

    @Bean
    public MethodInvokingJobDetailFactoryBean quartzTestTask(){
        MethodInvokingJobDetailFactoryBean m1 = new MethodInvokingJobDetailFactoryBean();
        m1.setGroup("MM_Job");
        m1.setTargetBeanName("quartzTestJob");
        m1.setTargetObject(quartzTestJob);
        m1.setTargetMethod("test");
        return m1;
    }

    @Bean
    public CronTriggerFactoryBean mmTestTrigger(){
        CustomCronTriggerFactoryBean cron1 = new CustomCronTriggerFactoryBean();
        cron1.setGroup("MM_Trigger");
        cron1.setCronExpression("0/20 * * * * ? *");
        cron1.setJobDetail(quartzTestTask().getObject());
        return cron1;
    }

    @Bean
    public SchedulerFactoryBean register(){
        final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTriggers(mmTestTrigger().getObject());
        return schedulerFactoryBean;
    }
}
