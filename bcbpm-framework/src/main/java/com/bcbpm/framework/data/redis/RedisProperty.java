/**
 * 
 */
package com.bcbpm.framework.data.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**<p>Title: RedisProperty</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 下午3:23:41
 * @version :
 * @description: redis 配置信息
 */
@Component
@ConfigurationProperties(RedisProperty.PREFIX)
public class RedisProperty{
    public static final String PREFIX = "spring.redis";
    // 启动模式 0 单机 1集群
    private Integer mode;
    // redis单机
    private String host;
    private int port;
    private String auth;
    private int commandTimeout;
    private int expireSeconds;

    // redis集群
    private String clusterNodes;
    // 默认集群连接超时时间
    private int clusterConnectTimeout;
    // 返回值的超时时间
    private int clusterSoTimeout;
    // 出现异常最大尝试次数
    private int clusterMaxAttempts;
    // 认证密码
    private String clusterAuth;

    public Integer getMode(){
        return mode;
    }

    public void setMode(Integer mode){
        this.mode = mode;
    }

    public String getClusterNodes(){
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes){
        this.clusterNodes = clusterNodes;
    }

    public int getCommandTimeout(){
        return commandTimeout;
    }

    public void setCommandTimeout(int commandTimeout){
        this.commandTimeout = commandTimeout;
    }

    public int getExpireSeconds(){
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds){
        this.expireSeconds = expireSeconds;
    }

    public int getClusterConnectTimeout(){
        return clusterConnectTimeout;
    }

    public void setClusterConnectTimeout(int clusterConnectTimeout){
        this.clusterConnectTimeout = clusterConnectTimeout;
    }

    public int getClusterSoTimeout(){
        return clusterSoTimeout;
    }

    public void setClusterSoTimeout(int clusterSoTimeout){
        this.clusterSoTimeout = clusterSoTimeout;
    }

    public int getClusterMaxAttempts(){
        return clusterMaxAttempts;
    }

    public void setClusterMaxAttempts(int clusterMaxAttempts){
        this.clusterMaxAttempts = clusterMaxAttempts;
    }

    public String getClusterAuth(){
        return clusterAuth;
    }

    public void setClusterAuth(String clusterAuth){
        this.clusterAuth = clusterAuth;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }

    public String getAuth(){
        return auth;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }

}
