package com.github.schedulejob.domain;

import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.util.ClassUtils;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class JobDomain {

    // job info
    private String name;
    private String groupName;
    private String targetClass;
    private String description;

    public JobDetail buildQuartzJobDetail(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean ();
        Class<?> clazz = null;
        try {
            clazz = ClassUtils.forName(this.targetClass,this.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        jobDetailFactoryBean.setJobClass(clazz);
        jobDetailFactoryBean.setGroup(this.groupName);
        jobDetailFactoryBean.setDescription(this.description);
        return jobDetailFactoryBean.getObject();
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

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "JobDomain{" +
                "description='" + description + '\'' +
                ", groupName='" + groupName + '\'' +
                ", name='" + name + '\'' +
                ", targetClass='" + targetClass + '\'' +
                '}';
    }
}
