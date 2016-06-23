package com.github.schedulejob.controller;

import com.github.schedulejob.domain.JobDetailDomain;
import com.github.schedulejob.service.QuartzJobDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET)
    public List<JobDetailDomain> list(){
        return quartzJobDetailService.queryJobList();
    }

}
