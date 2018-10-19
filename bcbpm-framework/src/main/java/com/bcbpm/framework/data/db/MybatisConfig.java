/**
 * 
 */
package com.bcbpm.framework.data.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.bcbpm.framework.data.enums.DatabaseType;

/**<p>Title: MybatisConfig</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 上午11:17:01
 * @version :
 * @description:
 * 
 * 通过读取application.properties文件生成两个数据源（mainDbDataSource、bkDbDataSource）
使用以上生成的两个数据源构造动态数据源dataSource
@Primary：指定在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@Autowire注解报错（一般用于多数据源的情况下）
@Qualifier：指定名称的注入，当一个接口有多个实现类的时候使用（在本例中，有两个DataSource类型的实例，需要指定名称注入）
@Bean：生成的bean实例的名称是方法名（例如上边的@Qualifier注解中使用的名称是前边两个数据源的方法名，而这两个数据源也是使用@Bean注解进行注入的）
通过动态数据源构造SqlSessionFactory和事务管理器（如果不需要事务，后者可以去掉）
 */
@Configuration
@MapperScan(basePackages = "com.bcbpm.dao")
public class MybatisConfig{

    @Autowired
    Environment environment;

    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean
    public DataSource mainDbDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc.driverClassName"));
        props.put("url", environment.getProperty("jdbc.url"));
        props.put("username", environment.getProperty("jdbc.username"));
        props.put("password", environment.getProperty("jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource bkDbDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc2.driverClassName"));
        props.put("url", environment.getProperty("jdbc2.url"));
        props.put("username", environment.getProperty("jdbc2.username"));
        props.put("password", environment.getProperty("jdbc2.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource statisticDbDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc3.driverClassName"));
        props.put("url", environment.getProperty("jdbc3.url"));
        props.put("username", environment.getProperty("jdbc3.username"));
        props.put("password", environment.getProperty("jdbc3.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**  
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     * @author jacky
     * @date 2018年10月19日 下午6:51:54
     * @param mainDbDataSource
     * @param bkDbDataSource
     * @param statisticDbDataSource
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("mainDbDataSource") DataSource mainDbDataSource, @Qualifier("bkDbDataSource") DataSource bkDbDataSource,
            @Qualifier("statisticDbDataSource") DataSource statisticDbDataSource){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.main, mainDbDataSource);
        targetDataSources.put(DatabaseType.back, bkDbDataSource);
        targetDataSources.put(DatabaseType.statistic, statisticDbDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(mainDbDataSource);// 默认的datasource设置为mainDbDataSource
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        //        fb.setDataSource(this.dataSource(mainDbDataSource, bkDbDataSource));
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        fb.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }

}
