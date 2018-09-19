package com.bcbpm.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Title: spring容器工具</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午7:11:32
 * @version :
 * @description: 获取spring容器，以访问容器中定义的其他bean  
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{

    // Spring应用上下文环境  
    private static ApplicationContext applicationContext;

    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     *  
     * @param applicationContext 
     */
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    /** 
     * @return ApplicationContext 
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /** 
     * 通过名称获取对象 
     * @param name 
     * @return Object 一个以所给名字注册的bean的实例 
     * @throws BeansException 
     */
    public static synchronized Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }

    /** 
     * 通过类型获取对象 
     * @param requiredType 
     * @return Object 一个以类型注册的bean的实例 
     * @throws BeansException 
     */
    public static synchronized Object getBean(Class<?> requiredType) throws BeansException{
        return applicationContext.getBean(requiredType);
    }

}
