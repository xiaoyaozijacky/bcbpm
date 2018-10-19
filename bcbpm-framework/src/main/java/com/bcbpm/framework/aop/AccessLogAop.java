/**
 * 
 */
package com.bcbpm.framework.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcbpm.framework.log.RecordLogService;
import com.bcbpm.model.log.AccessLog;
import com.bcbpm.util.IpAddressUtil;

/**<p>Title: AccessLogAop</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午2:26:38
 * @version :
 * @description: 系统访问日志拦截器
 */

@Aspect
@Order(3)
@Component
public class AccessLogAop{
    //    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RecordLogService recordLogService;

    @Pointcut("execution(public * com.bcbpm.controller..*.*(..))")
    public void accessLog(){
    }

    @Before("accessLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        AccessLog accessLog = new AccessLog();
        //        if("POST".equals(request.getMethod())){ //记录post方法
        //        // 记录下请求内容
        //        logger.info("请求URL : " + request.getRequestURL());
        //        logger.info("请求IP : " + IpAddressUtil.getIpAdrress(request));
        //        logger.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        accessLog.setUrl(request.getRequestURL().toString());
        accessLog.setIp(IpAddressUtil.getIpAdrress(request));
        accessLog.setControllerMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
        if(joinPoint.getArgs().length > 0){
            Object[] paramValues = joinPoint.getArgs();
            String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
            accessLog.setParamNames(paramNames);
            accessLog.setParamValues(paramValues);
            //            for(int i = 0; i < joinPoint.getArgs().length; i++){
            //                if(paramValues[i] instanceof HttpServletRequest || paramValues[i] instanceof HttpServletResponse){
            //                    continue;
            //                }
            //                logger.info("请求参数" + paramNames[i] + " : " + JSON.toJSONString(paramValues[i]));
            //            }
        }
        //        }
        accessLog.setStartTimeMs(System.currentTimeMillis());
        request.setAttribute("recordAccessLog", accessLog);
    }

    @AfterReturning(returning = "ret", pointcut = "accessLog()")
    public void doAfterReturning(Object ret) throws Throwable{
        // 处理完请求，返回内容
        //        logger.info("返回 : " + JSON.toJSONString(ret));
        recordLogService.recordLog(ret);
    }
}
