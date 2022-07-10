package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {
    //实例化记录日志的组件，对每一个类都单独实例化logger，不同类使用不同logger
    //static为了便于所有类去使用，final不可改变，接口名叫做Logger,变量名为logger
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);
        //传入一个类，这个类就是logger的名字，通常以当前类传入
        //这样不同的logger在不同的类下就会有一个区别
    @Test
    public void testLogger() {
        System.out.println(logger.getName()); //在控制台输出logger的名字

        /* 使用logger记录日志*/
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }
}

