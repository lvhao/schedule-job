package com.github.schedulejob.controller;

import com.github.schedulejob.common.Response;
import com.github.schedulejob.common.RetCodeConst;
import com.github.schedulejob.domain.JobKeyDomain;
import com.github.schedulejob.domain.JobWithTriggersDomain;
import com.github.schedulejob.util.ResponseBuilder;
import com.github.schedulejob.service.QuartzJobDetailService;
import org.quartz.JobKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public Response<List<JobWithTriggersDomain>> list(){
        List<JobWithTriggersDomain> jobWithTriggersDomainList = quartzJobDetailService.queryJobList();
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDomainList)
                .build();
    }

    /**
     * 查询指定jobKey jobDetail
     * @param name
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/{groupName}/{name}", method = RequestMethod.GET)
    public Response<JobWithTriggersDomain> queryByJobKey(
            @PathVariable String name,
            @PathVariable String groupName){
        JobKey jobKey = new JobKey(name,groupName);
        JobWithTriggersDomain jobWithTriggersDomain = quartzJobDetailService.queryByKey(jobKey);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDomain)
                .build();
    }

    /**
     * 添加任务
     * @param jobWithTriggersDomain
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response add(@RequestBody JobWithTriggersDomain jobWithTriggersDomain){
        quartzJobDetailService.add(jobWithTriggersDomain);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .build();
    }

    /**
     * 批量删除Job
     * @param jobKeyDomains
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Response delete(@RequestBody List<JobKeyDomain> jobKeyDomains){
        List<JobKey> jobkeys = jobKeyDomains.stream().map(jkDomain -> {
            JobKey jobKey = new JobKey(jkDomain.getName(),jkDomain.getGroup());
            return jobKey;
        }).collect(Collectors.toList());
        quartzJobDetailService.remove(jobkeys);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .build();
    }
}
