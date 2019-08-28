package com.zhq;

import org.activiti.engine.*;
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

    /**
     * 提供一系列管理流程定义和流程部署的API。
     */
    @Autowired
    private RepositoryService repositoryService;//存储服务

    /**
     * 在流程运行时对流程实例进行管理与控制。
     */
    @Autowired
    private RuntimeService runtimeService;//运行时服务

    /**
     * 对流程任务进行管理，例如任务提醒、任务完成和创建任务分本任务等。
     */
    @Autowired
    private TaskService taskService;//任务服务

    /**
     * 提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户以及它们之间的关系。
     */
    @Autowired
    private IdentityService identityService;//角色服务

    /**
     * 提供对流程引擎进行管理和维护的服务。
     */
    @Autowired
    private ManagementService managementService;//管理和维护服务

    /**
     * 对流程的历史数据进行操作，包括查询、删除这些历史数据。
     */
    @Autowired
    private HistoryService historyService;//历史数据服务

    /**
     * 使用该服务，可以不需要重新部署流程模型，就可以实现对流程模型的部分修改
     */
    @Autowired
    private DynamicBpmnService dynamicBpmnService;//流程模型服务

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
