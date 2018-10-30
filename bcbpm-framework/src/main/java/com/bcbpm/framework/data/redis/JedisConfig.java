package com.bcbpm.framework.data.redis;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**<p>Title: JedisConfig</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 下午3:32:32
 * @version :
 * @description:
 */
@Configuration
public class JedisConfig{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisProperty redisProperties;

    /**
     * @Title: 这里返回的jedisCluster是单例的，并且可以直接注入到使用类中 
    * @param @return   
    * @return JedisCluster    
    * @author jacky
    * @date 2018年9月14日 下午3:40:40
     */
    @Bean
    public JedisCluster getJedisCluster(){
        //        if(redisProperties.getMode() == 0){
        //            return null;
        //        }
        logger.info("redis 集群启动: ");
        String[] serverArray = redisProperties.getClusterNodes().split(",");//获取redis集群服务器组ip
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        for(String ipPort : serverArray){
            String[] ipPortArray = ipPort.split(":");
            logger.info(ipPortArray[0].trim() + ": " + ipPortArray[1].trim());
            nodes.add(new HostAndPort(ipPortArray[0].trim(), Integer.parseInt(ipPortArray[1].trim())));
        }
        // 单机模式则 不正式建立集群连接
        if(redisProperties.getMode() == 0){
            return new JedisCluster(nodes);
        }
        JedisPoolConfig config = new JedisPoolConfig();
        //      config.setMaxActive(500);    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(1024);
        config.setMaxIdle(200); //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
        config.setMaxWaitMillis(10000); //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setTestOnBorrow(false); //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        //      jedisCluster = new JedisCluster(clusterNodes,timeout,config);//不带密码的访问
        // 带密码的访问 soTimeout: 返回值的超时时间 3秒   maxAttempts：出现异常最大重试次数5
        return new JedisCluster(nodes, redisProperties.getCommandTimeout(), redisProperties.getClusterSoTimeout(), redisProperties.getClusterMaxAttempts(), redisProperties.getClusterAuth(), config);
    }

    /** 
     * 构建redis连接池 
     *  
     * @param ip 
     * @param port 
     * @return JedisPool 
     */
    @Bean
    public JedisPool initPool(){
        //        if(redisProperties.getMode() == 1){
        //            return null;
        //        }
        logger.info("redis 单机启动: " + redisProperties.getHost() + ":" + redisProperties.getPort());
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
        //            config.setMaxActive(500);  
        config.setMaxTotal(1024);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
        config.setMaxIdle(200);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
        //            config.setMaxWait(1000 * 100);  
        config.setMaxWaitMillis(10000);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
        config.setTestOnBorrow(false);
        return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), 10000, redisProperties.getAuth()); //new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
    }

}
