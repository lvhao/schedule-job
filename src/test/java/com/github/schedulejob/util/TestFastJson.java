package com.github.schedulejob.util;

import com.alibaba.fastjson.JSON;
import org.quartz.JobKey;

/**
 * Created by root on 2016/6/25 0025.
 */
public class TestFastJson {
    public static void main(String[] args) {
        /*JobWithTriggersDomain jtd = new JobWithTriggersDomain();
        JobDomain jd = new JobDomain();
        jd.setGroupName("MM_JOB");
        jd.setName("mm_test_job");
        jd.setTargetClass(com.github.schedulejob.schedule.QuartzTestJob.class.getCanonicalName());
        jd.setDescription("MM test job");
        jtd.setJobDomain(jd);

        Set<TriggerDomain> triggerDomainSet = Sets.newHashSet();
        TriggerDomain td = new TriggerDomain();
        td.setGroupName("MM_TRIGGER");
        td.setName("mm_test_job_trigger");
        td.setCronExpression("0/10 * * * * *");
        td.setDescription("MM test job trigger");
        triggerDomainSet.add(td);
        jtd.setTriggerDomainSet(triggerDomainSet);
        String rt =JSON.toJSONString(jtd);
        System.out.println("rt = " + rt);*/
        JobKey jk = new JobKey("name","group");
        JSON.toJSONString(jk);
        System.out.println("jk = " + jk);
    }
}
