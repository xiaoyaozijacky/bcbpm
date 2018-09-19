/**
 * 
 */
package com.bcbpm.framework.aop;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bcbpm.framework.data.db.DatabaseContextHolder;
import com.bcbpm.framework.data.enums.DatabaseType;

/**<p>Title: DynamicDataSourceAop</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月19日 上午11:18:42
 * @version :
 * @description: 动态切换数据源入口
 */
@Aspect
@Component
@Order(1)
public class DynamicDataSourceAop{
    private AtomicLong atCnt = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution (* *..service*..*(..))")
    public void setDataSourceKey(JoinPoint pjp) throws Throwable{
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object target = pjp.getTarget();

        logger.info("target class: " + target.getClass() + "target package: " + target.getClass().getPackage().getName() + "，method.getName(): " + method.getName());
        if(method.getName().equals("addBatchLogStatistics")){
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);//设置为日志库
        }else if(target.getClass().getPackage().getName().startsWith("com.bcbpm.activiti")){
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);//设置为工作流库
        }else{
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);//默认走主库
        }

        //        if(target instanceof IBaseService){
        if(chkWritePrefix(method.getName())){
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        }else{
            DatabaseContextHolder.setDatabaseType(getReadDb());
        }
        //        }
    }

    //判断方法前缀是否需要走主库(增、删、改)
    private boolean chkWritePrefix(String name){
        boolean flg = false;
        if(name.startsWith("add") || name.startsWith("save") || name.startsWith("del") || name.startsWith("upd")){
            flg = true;
        }
        return flg;
    }

    // 设置从库读取策略 
    private DatabaseType getReadDb(){
        // 轮询策略,将前端过来的请求平均分配到两台从机上(目前集群情况：一主两从)
        long cnt = atCnt.addAndGet(1);
        DatabaseType readDB = null;
        if(cnt % 2 == 0){
            readDB = DatabaseType.back;
        }else{
            readDB = DatabaseType.back;
        }
        logger.info("当前读取的DB是：" + readDB);
        return readDB;
    }
}
