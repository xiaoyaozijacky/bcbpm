package com.bcbpm.framework.data.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcbpm.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisClusterException;
import redis.clients.jedis.exceptions.JedisClusterMaxRedirectionsException;

/**
 * redis集群服务实现类
 * @author jacky
 * */
@Component("redisClusterService")
public class RedisClusterServiceImpl implements IRedisService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisCluster jedisCluster;//单例的
    @Autowired
    private RedisProperty redisProperty;//配置信息

    public String set(String key, String value){
        String statusCode = null;
        try{
            if(key != null && value != null){
                statusCode = jedisCluster.set(key, value);
            }else{
                logger.info("redis调用set方法没有成功,key:[" + key + "]" + "value:[" + value + "]");
            }
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return set(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return set(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("set失败,key:[" + key + "]" + "value:[" + value + "]");
        }
        return statusCode;
    }

    @Override
    /**
     * 放入redis缓存数据（补充扩展参数）
     * @param key
     * @param value
     * @param nxxx - NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist
     * @param expx - EX|PX, expire time units: EX = seconds; PX = milliseconds
     * @param time - expire time in the units of expx
     * @return  Status code reply
     * */
    public String set(String key, String value, String nxxx, String expx, long time){
        String statusCode = null;
        try{
            statusCode = jedisCluster.set(key, value, nxxx, expx, time);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return set(key, value, nxxx, expx, time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return set(key, value, nxxx, expx, time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("set失败,key:[" + key + "]" + "value:[" + value + "]" + "nxxx:[" + nxxx + "]" + "expx:[" + expx + "]" + "time:[" + time + "]");
        }finally{
        }
        return statusCode;
    }

    public Long batchDel(String prefix){
        Long status = null;
        //        Jedis resource = null;
        //        boolean broken = false;
        //        try{
        //            resource = jedisCluster.getResource();
        //            Set<String> set = resource.keys(prefix + "*");
        //            Iterator<String> it = set.iterator();
        //            while (it.hasNext()){
        //                String keyStr = it.next();
        //                status = resource.del(keyStr);
        //            }
        //        }catch(Exception e){
        //            broken = RedisManager.getInstance().handleJedisException(e);
        //            logger.error("批量删除key失败");
        //        }finally{
        //            //释放redis对象,返还到连接池  
        //            RedisManager.getInstance().close(resource, broken);
        //        }
        return status;
    }

    @Override
    public String setObject(String key, Object value){
        return setObject(key, value, null, null, 0);
    }

    @Override
    /**
     * 放入redis缓存数据
     * 可以将业务对象序列化存储，取出时可以反序列化
     * @param key
     * @param value
     * @param nxxx - NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist
     * @param expx - EX|PX, expire time units: EX = seconds; PX = milliseconds
     * @param expireTime - expire time in the units of expx，默认milliseconds
     * @return  Status code reply
     * */
    public String setObject(String key, Object value, String nxxx, String expx, long expireTime){
        String statusCode = null;
        try{
            if(key != null && value != null){
                if(expireTime == 0){
                    statusCode = jedisCluster.set(key.getBytes("UTF-8"), SerializeUtil.serialize(value));
                }else{
                    statusCode = jedisCluster.set(key.getBytes("UTF-8"), SerializeUtil.serialize(value), nxxx.getBytes("UTF-8"), expx.getBytes("UTF-8"), expireTime);
                }
            }else{
                logger.info("redis调用setObject方法没有成功,key:[" + key + "]" + "value:[" + value + "]");
            }
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return setObject(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return setObject(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("setObject失败,key:[" + key + "]" + "value:[" + value + "]");
        }finally{
        }
        return statusCode;
    }

    @Override
    public Set<String> getkeys(String key){
        Set<String> keySet = new HashSet<String>();
        try{
            //获取所有连接池节点
            Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
            //遍历所有连接池，逐个进行模糊查询
            for(String k : nodes.keySet()){
                JedisPool pool = nodes.get(k);
                //获取Jedis对象，Jedis对象支持keys模糊查询
                Jedis connection = pool.getResource();
                try{
                    keySet.addAll(connection.keys(key));
                }catch(Exception e){
                    logger.error("获取失败", e);
                }finally{
                    //一定要关闭连接！
                    connection.close();
                }
            }
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("get失败,key:[" + key + "]");
        }finally{
        }
        return keySet;
    }

    @Override
    public String get(String key){
        String value = null;
        try{
            value = jedisCluster.get(key);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return get(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return get(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("get失败,key:[" + key + "]");
        }finally{
        }
        return value;
    }

    @Override
    public Object getObject(String key){
        Object value = null;
        try{
            value = SerializeUtil.unserialize(jedisCluster.get(key.getBytes("UTF-8")));
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return getObject(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return getObject(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("getObject失败,key:[" + key + "]");
        }finally{
        }
        return value;
    }

    @Override
    public Boolean exists(String key){
        boolean exists = false;
        try{
            exists = jedisCluster.exists(key);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return exists(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return exists(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("exists失败,key:[" + key + "]");
        }finally{
        }
        return exists;
    }

    @Override
    public Long del(String key){
        Long status = null;
        try{
            status = jedisCluster.del(key);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return del(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return del(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            logger.error("del失败,key:[" + key + "]");
        }finally{
        }
        return status;
    }

    @Override
    public Long del(String... keys){
        Long status = null;
        try{
            status = jedisCluster.del(keys);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return del(keys);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return del(keys);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return status;
    }

    /**
     * left顺序存储队列
     */
    public void lpush(String key, byte[] value){
        try{
            jedisCluster.lpush(key.getBytes("UTF-8"), value);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                lpush(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                lpush(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    /**
     *  right反向存储队列
     */
    public void rpush(String key, byte[] value){
        try{
            jedisCluster.rpush(key.getBytes("UTF-8"), value);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                rpush(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                rpush(key, value);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    /**
     * 获取队列数据
     */
    public List<byte[]> lrange(String key){
        List<byte[]> list = null;
        try{
            list = jedisCluster.lrange(key.getBytes("UTF-8"), 0, -1);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return lrange(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return lrange(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return list;
    }

    /**
     * right反向获取队列数据
     */
    public byte[] lpop(String key){
        byte[] bytes = null;
        try{
            bytes = jedisCluster.lpop(key.getBytes("UTF-8"));
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return lpop(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return lpop(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return bytes;
    }

    /**
     * left顺序获取队列数据
     */
    public byte[] rpop(String key){
        byte[] bytes = null;
        try{
            bytes = jedisCluster.rpop(key.getBytes("UTF-8"));
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return rpop(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return rpop(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return bytes;
    }

    /**
     * left顺序获取队列数据
     */
    public List<byte[]> blpop(byte[]... keys){
        List<byte[]> result = null;
        try{
            result = jedisCluster.blpop(0, keys);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return blpop(keys);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return blpop(keys);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return result;
    }

    @Override
    public Long incr(String key){
        Long cnt = null;
        try{
            cnt = jedisCluster.incr(key);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return incr(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return incr(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return cnt;
    }

    @Override
    public Long llen(String key){
        Long len = null;
        try{
            len = jedisCluster.llen(key);
        }catch(JedisClusterMaxRedirectionsException jmre){
            try{
                // 重连次数超过redis集群环境主从服务个数
                logger.info("too many Cluster redirections,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return llen(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(JedisClusterException jce){
            try{
                //redis服务器的哨兵机制，还没有完成挂机master替换或者替换失败 此时客户端请求从挂机的master中获取数据，所得到的结果就是the cluster is down
                logger.info("the cluster is down,waiting time：" + redisProperty.getClusterConnectTimeout() + "ms");
                Thread.sleep(redisProperty.getClusterConnectTimeout());
                return llen(key);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return len;
    }
}
