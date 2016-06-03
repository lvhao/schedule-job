package com.github.schedulejob.schedule;

import org.springframework.stereotype.Component;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-6-2 21:14
 */
@Component
public class QuartzTestJob {
    private static final int cnt = 0;
    public void test(){
        System.out.println("TEST !!!!!!");
    }
}
