package com.lvhao.schedulejob.controller;

import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.common.RetCodeConst;
import com.lvhao.schedulejob.domain.job.JobWithTriggersDO;
import com.lvhao.schedulejob.service.QuartzJobDetailService;
import com.lvhao.schedulejob.util.ResponseBuilder;
import com.google.common.collect.Lists;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * quartz api
 *
 * @author: lvhao
 * @since: 2016-6-23 20:18
 */
@RestController
@RequestMapping("/jobs")
public class QuartzJobDetailController {

    @Autowired
    private QuartzJobDetailService quartzJobDetailService;

    /**
     * 任务列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<JobWithTriggersDO>> list(){
        List<JobWithTriggersDO> jobWithTriggersDOList = quartzJobDetailService.queryJobList();
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDOList)
                .build();
    }

    /**
     * 查询指定jobKey jobDetail
     * @param name
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/{groupName}/{name}", method = RequestMethod.GET)
    public Response<JobWithTriggersDO> queryByJobKey(
            @PathVariable String name,
            @PathVariable String groupName){
        JobKey jobKey = new JobKey(name,groupName);
        JobWithTriggersDO jobWithTriggersDO = quartzJobDetailService.queryByKey(jobKey);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDO)
                .build();
    }

    /**
     * 添加任务
     * @param jobWithTriggersDO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response add(@RequestBody JobWithTriggersDO jobWithTriggersDO){
        quartzJobDetailService.add(jobWithTriggersDO);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .build();
    }

    /**
     * 批量删除Job
     * @param jobKeyGroups
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Response delete(@RequestBody Map<String,List<String>> jobKeyGroups){
        List<JobKey> jobKeys = Lists.newArrayList();
        jobKeyGroups.forEach((k,v) ->
            v.forEach(name -> {
                JobKey jobKey = new JobKey(name,k);
                jobKeys.add(jobKey);
            })
        );
        quartzJobDetailService.remove(jobKeys);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .build();
    }
}
