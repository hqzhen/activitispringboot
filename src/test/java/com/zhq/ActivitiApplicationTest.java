package com.zhq;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: activitispringboot
 * @description: 单元测试
 * @author: hq.zheng
 * @create: 2019-08-27 21:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiApplicationTest {
    private  static final Logger LOGGER=LoggerFactory.getLogger(ActivitiApplicationTest.class);
    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void testRuntimeService(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        LOGGER.info("processInstance = {}",processInstance);
    }
}
