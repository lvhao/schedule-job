package com.lvhao.schedulejob.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-22 12:34
 */
public class TestElapsedTimeUtils {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {
        final AtomicInteger ai = new AtomicInteger(65);

        for (int i = 0; i < 2; i++) {
            EXECUTOR.submit(()->{
                final Thread currentThread = Thread.currentThread();
                int index = ai.getAndIncrement();
                String n = String.valueOf((char)index);
                currentThread.setName("测试线程~"+n);
                ElapsedTimeUtils.time("x");
                ElapsedTimeUtils.timeEnd("x");
                ElapsedTimeUtils.time(n);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ElapsedTimeUtils.timeEnd(n);
            });
        }
        EXECUTOR.shutdown();
    }
}
