/**
 * 
 */
package com.bcbpm.framework.interceptor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**<p>Title: 注册拦截器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午8:21:24
 * @version :
 * @description:
 */
@Configuration
public class RegisterInterceptor implements WebMvcConfigurer{

    /**
     * 前台url请求记录拦截器
     * @return
     */
    @Bean
    public AccessRecordInterceptor accessRecordInterceptor(){
        return new AccessRecordInterceptor();
    }

    /**
     * 会话信息校验拦截器
     * @return
     */
    @Bean
    public SessionInterceptor sessionInterceptorterceptor(){
        return new SessionInterceptor();
    }

    /**
     * url请求权限拦截器
     * @return
     */
    @Bean
    public UrlAuthorityInterceptor urlAuthorityInterceptor(){
        return new UrlAuthorityInterceptor();
    }

    /**
     * 添加拦截器组合
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AccessRecordInterceptor());
        registry.addInterceptor(new SessionInterceptor());
        registry.addInterceptor(new UrlAuthorityInterceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        System.out.println("=======================加载自定义处理器configureMessageConverters====================");
        converters.clear();
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    /**
     * 默认使用MappingJackson2HttpMessageConverter对返回前台的数据进行json转换处理
     * */
    private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        return messageConverter;
    }
}