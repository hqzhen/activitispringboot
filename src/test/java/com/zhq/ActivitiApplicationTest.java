package com.zhq;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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
 * @author: HQ Zheng
 * @create: 2019-08-27 21:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiApplicationTest {

    private  static final Logger LOGGER=LoggerFactory.getLogger(ActivitiApplicationTest.class);

    @Autowired
    private RepositoryService repositoryService;//存储服务

    @Autowired
    private RuntimeService runtimeService;//运行时服务

    @Autowired
    private TaskService taskService;//任务服务

    /**
     * 测试部署一个bpmn流程文件
     * 部署后在act_re_deployment、act_re_procdef、act_ge_bytearray生成记录
     */
    @Test
    public void testInsertFirstBPMN(){
        //部署一个流程
        repositoryService.createDeployment().addClasspathResource("processes/firstbpm.bpmn").deploy();
    }

    /**
     * 测试启动一个流程，然后正常走完流程
     */
    @Test
    public void testStartFirstBPMN(){
        //通过key启动一个流程
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess");
        //获取当前流程节点
        Task task = taskService.createTaskQuery().processInstanceId(myProcess.getId()).singleResult();
        while (Boolean.TRUE){
            if(task==null){
                System.out.println("流程结束："+task);
                break;
            }else{
                System.out.println("当前流程节点："+task.getName());
                //完成当前节点
                taskService.complete(task.getId());
                //再次获取当前节点
                task = taskService.createTaskQuery().processInstanceId(myProcess.getId()).singleResult();
            }
        }
    }

    @Test
    public void testRuntimeService(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
        LOGGER.info("processInstance = {}",processInstance);
    }
}
