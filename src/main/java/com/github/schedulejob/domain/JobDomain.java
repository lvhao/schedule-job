package com.github.schedulejob.domain;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.springframework.util.ClassUtils;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-23 20:59
 */
public class JobDomain {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JobDomain.class);

    // job info
    private String name;
    private String groupName;
    private String targetClass;
    private String description;

    public static JobDomain buildJobDomain(JobDetail jobDetail){
        JobDomain domain = new JobDomain();
        JobKey jk = jobDetail.getKey();
        domain.setName(jk.getName());
        domain.setGroupName(jk.getGroup());
        domain.setTargetClass(jobDetail.getJobClass().getCanonicalName());
        domain.setDescription(jobDetail.getDescription());
        return domain;
    }

    public JobDetail convert2QuartzJobDetail(){
        Class<? extends Job> clazz = null;
        try {
            clazz = (Class<Job>)ClassUtils.forName(this.targetClass,this.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("加载类错误",e);
        }
        return JobBuilder.newJob()
                .ofType(clazz)
                .withIdentity(this.name,this.getGroupName())
                .withDescription(this.description)
                .build();
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
        final StringBuffer sb = new StringBuffer("JobDomain{");
        sb.append("name='").append(name).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", targetClass='").append(targetClass).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
