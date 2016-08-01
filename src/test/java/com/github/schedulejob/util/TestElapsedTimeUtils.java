package com.github.schedulejob.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-22 12:34
 */
public class TestElapsedTimeUtils {
    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        EXECUTOR.scheduleAtFixedRate(()->{
            final Thread currentThread = Thread.currentThread();
            currentThread.setName("测试线程~");
            ElapsedTimeUtils.time("x");
            ElapsedTimeUtils.timeEnd("x");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1,2, TimeUnit.SECONDS);
    }
}
