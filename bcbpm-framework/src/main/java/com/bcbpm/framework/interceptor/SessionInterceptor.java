package com.bcbpm.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Title: 会话信息校验拦截器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午8:15:33
 * @version :
 * @description: 检查当前用户是否处于登录有效期状态，要不是则提示用户进行登录处理
 */
public class SessionInterceptor extends HandlerInterceptorAdapter{

    Logger logger = LoggerFactory.getLogger(getClass());

    //    @Autowired
    //    private ICacheStrategyService cacheRoleService;
    //    @Autowired
    //    private ISessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        logger.info("进入会话校验拦截器");
        // TODO : 待缓存设置完成后进行配置
        String url = request.getRequestURI().toString();
        // 过滤掉不需要登录拦截的url访问
        if(url.equals("/xxx/xxx")){
            return true;
        }else{
            //            Map user = (Map) SessionDeal.getNowUserFront(request);
            //            String userId =  user.get("userId");
            //            if(!StringUtils.isEmpty(token)){
            //                return true;
            //            }

            //            response.sendRedirect("/login/index");
            //            return false;
        }
        return true;

    }

}