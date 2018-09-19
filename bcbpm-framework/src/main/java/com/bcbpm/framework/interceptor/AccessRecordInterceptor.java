package com.bcbpm.framework.interceptor;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bcbpm.framework.data.redis.RedisClient;
import com.bcbpm.framework.data.redis.RedisKey;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.framework.spring.SpringContextUtil;
import com.bcbpm.model.user.User;
import com.bcbpm.util.SerializeUtil;
import com.bcbpm.util.TimeUtil;

/**
 * <p>Title: 前台url请求记录拦截器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午5:42:46
 * @version :
 * @description:对前台发起的url请求进行信息记录拦截器
 */
public class AccessRecordInterceptor extends HandlerInterceptorAdapter{
    Logger logger = LoggerFactory.getLogger(getClass());
    // TODO: 注入失败，后续再查看原因
    //    @Autowired
    //    private SessionDeal sessionDeal = null;
    //    @Autowired
    //    private RedisClient redisClient = null;

    private SessionDeal sessionDeal = (SessionDeal) SpringContextUtil.getBean("sessionDeal");
    private RedisClient redisClient = (RedisClient) SpringContextUtil.getBean("redisClient");

    /**
     * 在请求执行之前记录信息
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        logger.info("进入url请求信息记录拦截器");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        if(handler instanceof HandlerMethod){
            StringBuffer sb = new StringBuffer();
            sb.append("-----------------------").append(TimeUtil.getDateTime()).append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            String requestParams = getParamString(request.getParameterMap());//请求入参
            request.setAttribute("requestParams", requestParams);
            sb.append("Params    : ").append(requestParams).append("\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            logger.info(sb.toString());
        }
        return true;
    }

    /**
     * @Title: 获得request里的请求参数 
    * @param @param map
    * @param @return   
    * @return String    
    * @author jacky
    * @date 2018年9月13日 下午5:38:39
     */
    private String getParamString(Map<String, String[]> map){
        StringBuilder sb = new StringBuilder();
        for(Entry<String, String[]> e : map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                sb.append(value[0]).append("\t");
            }else{
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    // after the handler is executed
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if(handler instanceof HandlerMethod){
            HandlerMethod h = (HandlerMethod) handler;
            StringBuilder sb = new StringBuilder();
            sb.append("-----------------------").append(TimeUtil.getDateTime()).append("-------------------------------------\n");
            sb.append("costTime  : ").append(executeTime).append("ms").append("\n");
            sb.append("-------------------------------------------------------------------------------");
            logger.info(sb.toString());
            //进行统计处理
            dealSta(h.getBean().getClass().getName(), h.getMethod().getName(), startTime, endTime, request);
        }
    }

    /**
     * @Title: 采集访问controller的记录信息 
    * @param @param controllerName
    * @param @param methodName
    * @param @param startTime
    * @param @param endTime
    * @param @param request   
    * @return void    
    * @author jacky
    * @date 2018年9月13日 下午5:37:05
     */
    private void dealSta(String controllerName, String methodName, long startTime, long endTime, HttpServletRequest request){
        String requestParams = (String) request.getAttribute("requestParams");
        String uri = request.getRequestURI();
        StatisticsVO svo = new StatisticsVO();
        svo.setControllerName(controllerName);
        svo.setUri(uri);
        svo.setMethodName(methodName);
        svo.setAccessTime(TimeUtil.getDateTime());
        svo.setMaxExcuteTime(endTime - startTime);
        svo.setRequestParams(requestParams);
        User user = (User) sessionDeal.getNowUserFront(request);
        if(user != null){
            svo.setEnterpriseBelong(user.getEnterpriseBelong());
            svo.setUserId(user.getUserId());
        }else{
            logger.info("The method " + methodName + "未登录执行");
        }
        redisClient.getClient().lpush(RedisKey.SESSION_FUNCTION_STATISTICS_QUEUE, SerializeUtil.serialize(svo));
    }
}
