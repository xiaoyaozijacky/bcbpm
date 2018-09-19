package com.bcbpm.controller.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title: UserController</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 上午9:18:16
 * @version :
 * @description:
 */
@Controller
@RequestMapping("/act")
@ResponseBody
public class ActivitiController{
    Logger logger = LoggerFactory.getLogger(getClass());

    // 流程引擎
    //    @Autowired
    //    private ProcessEngine processEngine;

    //    // 注入Service  
    @Autowired
    private RepositoryService repositoryService;//流程存储服务组件
    @Autowired
    private RuntimeService runtimeService;//运行时服务组件
    @Autowired
    private TaskService taskService;//流程任务组件

    // 测试activiti执行流程
    @RequestMapping("/doTask")
    public String doTask(){
        StringBuffer sb = new StringBuffer();
        // 得到流程存储服务组件
        //        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 得到运行时服务组件
        //        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 获取流程任务组件
        //        TaskService taskService = processEngine.getTaskService();
        // 部署流程文件
        repositoryService.createDeployment().addClasspathResource("processes/First.bpmn").deploy();
        // 启动流程 pi为流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process1");
        // 查询第一个任务 普通员工完成费用报销
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        sb.append("当前流程里的任务节点名称：" + task.getName());
        logger.info("当前流程里的任务节点名称：" + task.getName());
        //        Task task = taskService.createTaskQuery().singleResult();
        //        System.out.println("第一个任务完成前，当前任务名称：" + task.getName());
        // 完成第一个任务
        taskService.complete(task.getId());

        // 查询第二个任务 经理审核
        task = taskService.createTaskQuery().singleResult();
        sb.append(" 第二个任务完成前，当前任务名称：" + task.getName());
        logger.info("第二个任务完成前，当前任务名称：" + task.getName());
        // 完成第二个任务（流程结束）
        taskService.complete(task.getId());

        // 流程结束后查看任务是否还存在
        task = taskService.createTaskQuery().singleResult();
        //        sb.append(" /n 流程结束后，查找任务：" + task.getName());
        logger.info("流程结束后，查找任务：" + task);
        //        engine.close();
        return sb.toString();
    }

}