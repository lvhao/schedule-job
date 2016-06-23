package com.github.schedulejob.domain;

import java.util.List;

/**
 * 任务抽象业务类
 *
 * @author: lvhao
 * @since: 2016-6-23 20:43
 */
public class JobDetailDomain {

    // job info
    private JobDomain jobDomain;

    // trigger info
    private List<TriggerDomain> triggerDomainList;

    public JobDomain getJobDomain() {
        return jobDomain;
    }

    public void setJobDomain(JobDomain jobDomain) {
        this.jobDomain = jobDomain;
    }

    public List<TriggerDomain> getTriggerDomainList() {
        return triggerDomainList;
    }

    public void setTriggerDomainList(List<TriggerDomain> triggerDomainList) {
        this.triggerDomainList = triggerDomainList;
    }

    @Override
    public String toString() {
        return "JobDetailPO{" +
                "jobDomain=" + jobDomain +
                ", triggerDomainList=" + triggerDomainList +
                '}';
    }
}
