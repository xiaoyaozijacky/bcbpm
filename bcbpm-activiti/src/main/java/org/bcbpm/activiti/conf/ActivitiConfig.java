/**
 * 
 */
package org.bcbpm.activiti.conf;

//import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.transaction.PlatformTransactionManager;

/**<p>Title: ActivitiConfig</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月19日 下午7:50:58
 * @version :
 * @description:activiti配置文件
 */
@Configuration
public class ActivitiConfig{
    @Autowired
    private DataSource dataSource;

    //    @Autowired
    //    private ResourcePatternResolver resourceLoader;

    /**
    * 初始化配置，将创建28张表
    * @return
    */
    @Bean
    public StandaloneProcessEngineConfiguration processEngineConfiguration(){
        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);
        //        configuration.setCreateDiagramOnDeploy(false); 是否需要在部署时同时生成流程图
        return configuration;
    }

    @Bean
    public ProcessEngine processEngine(){
        return processEngineConfiguration().buildProcessEngine();
    }

    //    @Bean
    //    public ProcessEngine processEngine(PlatformTransactionManager transactionManager, DataSource dataSource) throws IOException{
    //        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
    //        //自动部署已有的流程文件
    //        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourceLoader.CLASSPATH_URL_PREFIX + "processes/*.bpmn");
    //        configuration.setTransactionManager(transactionManager);
    //        configuration.setDataSource(dataSource);
    //        configuration.setDatabaseSchemaUpdate("true");
    //        configuration.setDeploymentResources(resources);
    //        configuration.setDbIdentityUsed(false);
    //        return configuration.buildProcessEngine();
    //    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine){
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine){
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine){
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine){
        return processEngine.getHistoryService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine){
        return processEngine.getManagementService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine){
        return processEngine.getIdentityService();
    }

}
