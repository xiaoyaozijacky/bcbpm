/**
 * 
 */
package com.bcbpm.framework.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**<p>Title: RedisClient</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月15日 上午11:02:14
 * @version :
 * @description:
 */
@Component
public final class RedisClient{
    @Autowired
    private IRedisService redisService;//单机
    @Autowired
    private IRedisService redisClusterService;//集群

    @Autowired
    private RedisProperty redisProperties;

    /**
     * @Title: 获取redis服务 
    * @param @return   
    * @return IRedisService    
    * @author jacky
    * @date 2018年9月15日 上午11:09:51
     */
    public IRedisService getClient(){
        if(redisProperties.getMode() == 0){
            return redisService;
        }else if(redisProperties.getMode() == 1){
            return redisClusterService;
        }
        return null;
    }

}
