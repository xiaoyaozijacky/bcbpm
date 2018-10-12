package com.bcbpm.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Title: url请求权限拦截器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午4:54:50
 * @version :
 * @description: 当前拦截器用于拦截所有控制前台发起的url请求，根据当前登录的角色进行权限的判定，
 *                        看用户是否拥有该url请求的权限，有则通过请求继续进行系统处理，没有则拒绝访问并返回前台。
 */
public class UrlAuthorityInterceptor extends HandlerInterceptorAdapter{

    Logger logger = LoggerFactory.getLogger(getClass());

    //    @Autowired
    //    private ICacheStrategyService cacheRoleService;
    //    @Autowired
    //    private ISessionManager sessionManager;

    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     * @return 
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //        boolean flag = false;
        // TODO : 待缓存设置完成后进行配置
        String url = request.getRequestURI().toString();
        logger.info("进入url请求权限拦截器,当前url: " + url);
        //        Map user = null;
        //        String userId = null;
        //        String userName = null;
        //        //        user = (Map) sessionManager.getNowUserFront(request);
        //        //        userId = (String) user.get("userId");
        //        //        userName = (String) user.get("userName");
        //        if(url.equals("/metering/enterprise/addEnterprise")){// 特殊判断注册企业号时失败没有权限再次提交申请。
        //            String enterpriseBelong = (String) user.get("enterpriseBelong");
        //            if(enterpriseBelong.equals("") || enterpriseBelong == null || enterpriseBelong.equals("undefined")){
        //                return true;
        //            }
        //        }

        // 从缓存中取出当前角色权限数据
        //        CacheRole cacheRole = (CacheRole) cacheRoleService.getCache(GlobalConstant.CACHE_KEY_USER_ROLE, userid);
        //        if(cacheRole != null && cacheRole.getUrlMap() != null && cacheRole.getUrlMap().containsKey(url)){
        //            logger.info(userName + "拥有此url权限,访问地址为" + url);
        //            return true;
        //        }else{
        //            logger.info(userName + "没有此url权限,访问地址为" + url);
        //            flag = false;
        //            response.setContentType("application/json; charset=UTF-8"); // 转码
        //            response.getWriter().write("{\"authCheckCode\":\"1\",\"returnMsg\":\"没有权限\"}");
        //            response.getWriter().close();
        //        }
        //        return flag;
        return true;
    }

}