package com.github.schedulejob.controller;

import com.github.schedulejob.common.Response;
import com.github.schedulejob.common.RetCodeConst;
import com.github.schedulejob.domain.JobWithTriggersDomain;
import com.github.schedulejob.util.ResponseBuilder;
import com.github.schedulejob.service.QuartzJobDetailService;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/{jobKey}", method = RequestMethod.GET)
    public Response<List<JobWithTriggersDomain>> queryByJobKey(){
        List<JobWithTriggersDomain> jobWithTriggersDomainList = quartzJobDetailService.q();
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .withData(jobWithTriggersDomainList)
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
     * @param jobKeyList
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Response delete(@RequestBody List<JobKey> jobKeyList){
        quartzJobDetailService.remove(jobKeyList);
        return ResponseBuilder.newResponse()
                .withRetCode(RetCodeConst.OK)
                .build();
    }
}
