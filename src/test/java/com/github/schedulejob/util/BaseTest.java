package com.github.schedulejob.util;

import com.github.schedulejob.AppMain;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试基类
 *
 * @author: lvhao
 * @since: 2016-10-11 21:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppMain.class)
@WebAppConfiguration
public class BaseTest {
}
