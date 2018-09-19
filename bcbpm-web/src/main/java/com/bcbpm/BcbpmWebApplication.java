package com.bcbpm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Repository;

// 要是不做多数据源的话，需要把exclude = { DataSourceAutoConfiguration.class }去掉，同时打开sqlSessionFactoryRef = "sqlSessionFactory"
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//定义实体Bean扫描包路径
//@ComponentScan(basePackages = "com.bcbpm.*")
//@EntityScan(basePackages = "com.bcbpm.dao.*")
//@MapperScan(basePackages = "com.bcbpm.dao.*", sqlSessionFactoryRef = "sqlSessionFactory", annotationClass = Repository.class)
@MapperScan(basePackages = "com.bcbpm.dao.*", annotationClass = Repository.class)

public class BcbpmWebApplication{
    public static void main(String[] args){
        SpringApplication.run(BcbpmWebApplication.class, args);
    }

}
