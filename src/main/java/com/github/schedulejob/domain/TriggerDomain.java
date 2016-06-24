package com.github.schedulejob.domain;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class TriggerDomain {

    // trigger info
    private String name;
    private String groupName;
    private String cronExpression;
    private String description;

    public CronTrigger buildQuartzTrigger(JobDetail jobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression("0/20 * * * * ?");
        cronTriggerFactoryBean.setGroup("MM");
        return cronTriggerFactoryBean.getObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TriggerDomain{" +
                "cronExpression='" + cronExpression + '\'' +
                ", description='" + description + '\'' +
                ", groupName='" + groupName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
