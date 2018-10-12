package com.bcbpm.framework.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcbpm.framework.data.redis.RedisClient;
import com.bcbpm.framework.data.redis.RedisKey;
import com.bcbpm.util.CookieUtils;

import redis.clients.util.JedisClusterCRC16;

/**
 * <p>Title: SessionDeal</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月17日 下午10:31:04
 * @version :
 * @description: 集群session统一控制器
 */
@Component
public class SessionDeal{
    public Logger logger = LoggerFactory.getLogger(getClass());
    //SESSION 超时时间，单位秒，默认40分钟
    //  private static final long sessionTimeOut=40*60; 
    private static final long sessionTimeOut = 120 * 60;

    @Autowired
    private RedisClient redisClient;

    /***
     * 登录后台过程
     * @param req
     * @param userObj
     */
    public void loginBk(HttpServletRequest req, Object userObj){
        //        HttpSession session = req.getSession(true);
        HttpSession session = null;
        String sessionId = CookieUtils.getCookie("JSESSIONID", req);
        logger.info("session ID: " + sessionId);
        String clusterKey;
        if(sessionId != null && !sessionId.equals("")){
            clusterKey = sessionId + RedisKey.CLUSTER_JSESSIONID_BK;
        }else{
            session = req.getSession(true);
            clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_BK;
        }
        ClusterSessionObject cso = new ClusterSessionObject();
        cso.setAttribute("USEROBJ", userObj);
        // session key
        //        String clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_BK;
        logger.info("clusterKey: " + clusterKey + "对应的slot: " + JedisClusterCRC16.getSlot(clusterKey));
        redisClient.getClient().setObject(clusterKey, cso);
    }

    /***
     * 登录前台过程
     * @param req
     * @param userObj
     * 
     */
    public void loginFront(HttpServletRequest req, Object userObj){
        //        HttpSession session = req.getSession(true);
        HttpSession session = null;
        String sessionId = CookieUtils.getCookie("JSESSIONID", req);
        logger.info("session ID: " + sessionId);
        String clusterKey;
        if(sessionId != null && !sessionId.equals("")){
            clusterKey = sessionId + RedisKey.CLUSTER_JSESSIONID_FR;
        }else{
            session = req.getSession(true);
            clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_FR;
        }
        ClusterSessionObject cso = new ClusterSessionObject();
        cso.setAttribute("USEROBJ_FRONT", userObj);
        // session key
        //        clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_FR;
        logger.info("clusterKey: " + clusterKey + "对应的slot: " + JedisClusterCRC16.getSlot(clusterKey));
        redisClient.getClient().setObject(clusterKey, cso, "NX", "EX", sessionTimeOut);//设置session超时时间 单位秒
    }

    public void logOut(HttpServletRequest req){
        HttpSession session = req.getSession(true);
        String clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_BK;
        redisClient.getClient().del(clusterKey);
    }

    /***
     * 当前登录的用户
     * @param req
     * @return
     */
    public Object getNowUser(HttpServletRequest req){
        HttpSession session = req.getSession(true);
        String clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_BK;
        logger.info("getNowUser-----clusterKey: " + clusterKey + "对应的slot: " + JedisClusterCRC16.getSlot(clusterKey));
        ClusterSessionObject cso = (ClusterSessionObject) redisClient.getClient().getObject(clusterKey);
        return cso != null ? cso.getAttribute("USEROBJ") : null;
    }

    /***
     * 当前前台登陆的用户  map
     * @param req
     * @return
     */
    public Object getNowUserFront(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getNowUserFront(req);
    }

    /***
     * 当前前台登陆的用户  map
     * @param req
     * @return
     */
    public Object getNowUserFront(HttpServletRequest req){
        String sessionId = CookieUtils.getCookie("JSESSIONID", req);
        logger.info("session ID: " + sessionId);
        String clusterKey;
        if(sessionId != null && !sessionId.equals("")){
            clusterKey = sessionId + RedisKey.CLUSTER_JSESSIONID_FR;
        }else{
            HttpSession session = req.getSession(true);
            clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_FR;
        }
        logger.info("getNowUserFront-----clusterKey: " + clusterKey + "对应的slot: " + JedisClusterCRC16.getSlot(clusterKey));
        ClusterSessionObject cso = (ClusterSessionObject) redisClient.getClient().getObject(clusterKey);
        return cso != null ? cso.getAttribute("USEROBJ_FRONT") : null;
    }

    /***
     * 当前前台登陆的用户所属企业信息 
     * @param req
     * @return
     */
    public Object getLoginCompany(HttpServletRequest req){
        //        HttpSession session = req.getSession(true);
        String sessionId = CookieUtils.getCookie("JSESSIONID", req);
        logger.info("session ID: " + sessionId);
        String clusterKey;
        if(sessionId != null && !sessionId.equals("")){
            clusterKey = sessionId + RedisKey.CLUSTER_JSESSIONID_FR;
        }else{
            HttpSession session = req.getSession(true);
            clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_FR;
        }
        //        String clusterKey = session.getId() + RedisKey.CLUSTER_JSESSIONID_FR;
        logger.info("getLoginCompany-----clusterKey: " + clusterKey + "对应的slot: " + JedisClusterCRC16.getSlot(clusterKey));
        ClusterSessionObject cso = (ClusterSessionObject) redisClient.getClient().getObject(clusterKey);
        return cso != null ? cso.getAttribute("COMPANY") : null;
    }
}