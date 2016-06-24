package com.github.schedulejob.service;

import com.github.schedulejob.domain.JobDetailDomain;
import com.github.schedulejob.domain.JobDomain;
import com.github.schedulejob.domain.TriggerDomain;
import com.google.common.collect.Lists;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定时任务操作
 *
 * @author: lvhao
 * @since: 2016-6-23 19:58
 */
@Service
public class QuartzJobDetailService extends BaseService {

    // SchedulerFactoryBean 创建
    @Autowired
    private Scheduler scheduler;

    // 任务列表
    public List<JobDetailDomain> queryJobList(){
        List<JobDetailDomain> jobDetailDomainList = Lists.newArrayList();

        // 处理job
        Function<JobDetail,JobDomain> copyJobPropFun = jd -> {
            // job
            JobKey jk = jd.getKey();
            JobDomain jobDomain = new JobDomain();
            jobDomain.setName(jk.getName());
            jobDomain.setGroupName(jk.getGroup());
            jobDomain.setTargetClass(jd.getJobClass().getCanonicalName());
            jobDomain.setDescription(jd.getDescription());
            return jobDomain;
        };

        // 处理triggers
        Function<List<Trigger>,Set<TriggerDomain>> copyTriggersFun = trList -> {

            // triggers
            Set<TriggerDomain> tdSet = trList.stream().map(tr ->{
                TriggerDomain td = new TriggerDomain();
                if (tr instanceof CronTrigger) {
                    CronTrigger ctr = (CronTrigger) tr;
                    td.setCronExpression(ctr.getCronExpression());
                }
                TriggerKey trk = tr.getKey();
                td.setName(trk.getName());
                td.setGroupName(trk.getGroup());
                td.setDescription(tr.getDescription());
                return td;
            }).collect(Collectors.toSet());
            return tdSet;
        };

        // 数据处理
        Function<Set<JobKey>,List<JobDetailDomain>> copyPropFun = jbst -> {
            List<JobDetailDomain> jddList = Lists.newArrayList();
            jddList = jbst.stream().map(jk ->{
                JobDetail jd = null;
                List<Trigger> trList = Lists.newArrayList();
                try {
                    jd = scheduler.getJobDetail(jk);
                    trList = (List<Trigger>)scheduler.getTriggersOfJob(jk);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }

                // jobDetail
                JobDetailDomain jdd = new JobDetailDomain();
                jdd.setJobDomain(copyJobPropFun.apply(jd));
                jdd.setTriggerDomainSet(copyTriggersFun.apply(trList));
                return jdd;
            }).collect(Collectors.toList());
            return jddList;
        };

        try {
            Set<JobKey> jobSet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobDetailDomainList = copyPropFun.apply(jobSet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobDetailDomainList;
    }

    /**
     * 添加任务
     * @param jobDetailDomain
     */
    public void addJob(JobDetailDomain jobDetailDomain){
        JobDetail jobDetail = jobDetailDomain.getJobDomain().buildQuartzJobDetail();
        Set<CronTrigger> triggerSet = jobDetailDomain.getTriggerDomainSet().stream().map(td ->
            td.buildQuartzTrigger(jobDetail)
        ).collect(Collectors.toSet());
        try {
            scheduler.scheduleJob(jobDetail,triggerSet,true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 删除任务
    public void removeJob(){}

    // 停用任务
    public void disableJob(){}

    // 启用任务
    public void enableJob(){}

    // 触发任务
    public void triggerJobNow(){}
}
