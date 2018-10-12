/**
 * 
 */
package com.bcbpm.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.bcbpm.framework.interceptor.AccessRecordInterceptor;
import com.bcbpm.framework.interceptor.SessionInterceptor;
import com.bcbpm.framework.interceptor.UrlAuthorityInterceptor;

/**<p>Title: 注册拦截器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午8:21:24
 * @version :
 * @description:
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    //    private static final Charset UTF8 = Charset.forName("UTF-8");

    //    @Override
    //    public void addViewControllers(ViewControllerRegistry registry){
    //        registry.addViewController("/bcbpm").setViewName("bcbpmController");
    //        registry.addViewController("/app").setViewName("activitiController");
    //    }

    //    @Bean
    //    public ServletRegistrationBean<DispatcherServlet> servletRegistrationBean(DispatcherServlet dispatcherServlet){
    //        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
    //        //            servletServletRegistrationBean.addUrlMappings("/bcbpm/*");
    ////        servletServletRegistrationBean.addUrlMappings("/app/*");
    //        return servletServletRegistrationBean;
    //    }

    //  @Override
    //    public void addResourceHandlers(ResourceHandlerRegistry registry){
    //        registry.addResourceHandler("/**");
    //    }

    //
    //    @Bean
    //    public ServletRegistrationBean restServlet(){
    //        //注解扫描上下文
    //        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
    //        //base package
    //        applicationContext.scan("com.bcbpm");
    //        //通过构造函数指定dispatcherServlet的上下文
    //        DispatcherServlet rest_dispatcherServlet = new DispatcherServlet(applicationContext);
    //
    //        //用ServletRegistrationBean包装servlet
    //        ServletRegistrationBean registrationBean = new ServletRegistrationBean(rest_dispatcherServlet);
    //        registrationBean.setLoadOnStartup(1);
    //        //指定urlmapping
    //        registrationBean.addUrlMappings("/*");
    //        //指定name，如果不指定默认为dispatcherServlet
    //        //        registrationBean.setName("rest");
    //        return registrationBean;
    //    }

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
        //        String[] excludes = new String[] { "/", "user/login", "/login.html", "/static/**" };
        //        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes); 登录拦截器需要过滤这些页面

        //调试阶段关闭
        //                registry.addInterceptor(new AccessRecordInterceptor());
        //        registry.addInterceptor(new SessionInterceptor());
        registry.addInterceptor(new UrlAuthorityInterceptor());
    }

    //    @Override
    //    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
    //        System.out.println("=======================加载自定义处理器configureMessageConverters====================");
    //        converters.clear();
    //        converters.add(getMappingJackson2HttpMessageConverter());
    //        //        converters.add(getStringHttpMessageConverter());
    //    }

    //    @Override
    //    public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
    //        System.out.println("=======================加载自定义处理器configureMessageConverters====================");
    //                converters.clear();
    //                converters.add(getMappingJackson2HttpMessageConverter());
    //                converters.add(getStringHttpMessageConverter());
    //    }

    //    /**
    //     * 默认使用MappingJackson2HttpMessageConverter对返回前台的数据进行json转换处理
    //     * */
    //    private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(){
    //        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    //        return messageConverter;
    //    }

    //    /**
    //     * 补充使用StringHttpMessageConverter
    //     * */
    //    private StringHttpMessageConverter getStringHttpMessageConverter(){
    //        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    //        List<MediaType> list = new ArrayList<MediaType>();
    //        list.add(new MediaType("text", "plain", UTF8));
    //        list.add(new MediaType("text", "html", UTF8));
    //        list.add(new MediaType("application", "xml", UTF8));
    //        list.add(new MediaType("application", "json", UTF8));
    //        stringConverter.setSupportedMediaTypes(list);
    //        //stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
    //        //stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", UTF8)));
    //        //stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "xml",UTF8)));
    //        //stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json",UTF8)));
    //
    //        return stringConverter;
    //    }

    @Bean
    public SessionLocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        log.debug("Configuring localeChangeInterceptor");
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    //    @Bean
    //    public MultipartResolver multipartResolver(){
    //        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    //        multipartResolver.setMaxUploadSize(environment.getProperty("file.upload.max.size", Long.class));
    //        return multipartResolver;
    //    }

    //    @Bean
    //    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
    //        log.debug("Creating requestMappingHandlerMapping");
    //        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
    //        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
    //        requestMappingHandlerMapping.setRemoveSemicolonContent(false);
    //        Object[] interceptors = { localeChangeInterceptor() };
    //        requestMappingHandlerMapping.setInterceptors(interceptors);
    //        return requestMappingHandlerMapping;
    //    }
}