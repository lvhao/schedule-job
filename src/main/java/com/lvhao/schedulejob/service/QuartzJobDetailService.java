package com.lvhao.schedulejob.service;

import com.google.common.base.Throwables;
import com.lvhao.schedulejob.domain.job.JobWithTriggersDo;
import com.google.common.collect.Lists;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public List<JobWithTriggersDo> queryJobList(){
        List<JobWithTriggersDo> jobWithTriggersDoList = Lists.newArrayList();

        // 数据处理
        Function<Set<JobKey>,List<JobWithTriggersDo>> copyPropFun = jbst -> {
            List<JobWithTriggersDo> jddList = Lists.newArrayList();
            jddList = jbst.stream().map(jk ->{
                JobDetail jd = null;
                List<Trigger> trList = this.getTriggerByKey(jk);
                jd = this.getJobDetailByKey(jk);

                // jobDetail
                JobWithTriggersDo jobWithTriggersDo = new JobWithTriggersDo();
                jobWithTriggersDo.fillWithQuartzJobDetail.accept(jd);
                jobWithTriggersDo.fillWithQuartzTriggers.accept(trList);
                return jobWithTriggersDo;
            }).collect(Collectors.toList());
            return jddList;
        };

        try {
            Set<JobKey> jobSet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobWithTriggersDoList = copyPropFun.apply(jobSet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobWithTriggersDoList;
    }

    /**
     * 查询指定jobkey jobDetail
     * @param jobKey
     * @return
     */
    public JobWithTriggersDo queryByKey(JobKey jobKey){
        JobWithTriggersDo jobWithTriggersDo = new JobWithTriggersDo();
        JobDetail jobDetail = this.getJobDetailByKey(jobKey);
        if (Objects.nonNull(jobDetail)) {
            List<Trigger> triggerList = this.getTriggerByKey(jobKey);
            jobWithTriggersDo.fillWithQuartzJobDetail.accept(jobDetail);
            jobWithTriggersDo.fillWithQuartzTriggers.accept(triggerList);
        }
        return jobWithTriggersDo;
    }

    /**
     * 添加任务
     * @param jobWithTriggersDo
     */
    public boolean add(JobWithTriggersDo jobWithTriggersDo) {
        JobDetail jobDetail = jobWithTriggersDo.getJobDo().convert2QuartzJobDetail();
        Set<CronTrigger> triggerSet = jobWithTriggersDo.getTriggerDos().stream().map(td ->
            td.convert2QuartzTrigger(jobDetail)
        ).collect(Collectors.toSet());

        // 如果已经存在 则替换
        try {
            scheduler.scheduleJob(jobDetail,triggerSet,true);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除任务
     *
     * @param jobKeyList
     */
    public boolean remove(List<JobKey> jobKeyList){
        try {
            scheduler.deleteJobs(jobKeyList);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 停用任务
    public boolean disable(GroupMatcher<JobKey> matcher){
        try {
            scheduler.pauseJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 停用所有任务
    public boolean disableAll(){
        try {
            scheduler.pauseAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 启用任务
    public boolean enable(GroupMatcher<JobKey> matcher){
        try {
            scheduler.resumeJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 启用所有任务
    public boolean enableAll(){
        try {
            scheduler.resumeAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 立即触发任务
    public boolean triggerNow(JobKey jobKey, JobDataMap jobDataMap){
        try {
            scheduler.triggerJob(jobKey,jobDataMap);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据key 获取jobDetail
     * @param jobKey
     * @return
     */
    public JobDetail getJobDetailByKey(JobKey jobKey){
        JobDetail jd = null;
        try {
            jd = scheduler.getJobDetail(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jd;
    }

    /**
     * 根据key 获取job trigger
     * @param jobKey
     * @return
     */
    public List<Trigger> getTriggerByKey(JobKey jobKey){
        List<Trigger> triggerList = Lists.newArrayList();
        try {
            triggerList = (List<Trigger>)scheduler.getTriggersOfJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerList;
    }
}
