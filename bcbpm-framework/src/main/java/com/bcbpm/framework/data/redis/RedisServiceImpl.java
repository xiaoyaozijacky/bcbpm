package com.bcbpm.framework.data.redis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis服务实现类
 * @author jacky
 * */
@Service("redisService")
public class RedisServiceImpl implements IRedisService{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JedisPool jedisPool;//jedis连接池

    /**
     * 获取redis客户端实例
     * */
    public synchronized Jedis getResource(){
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            if(resource == null){
                throw new BusinessException("redis 获取 resource 失败, 请检查redis服务!");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis 获取 resource 失败");
        }
        return resource;
    }

    @Override
    public String set(String key, String value){
        String statusCode = null;
        Jedis resource = null;
        try{
            if(key != null && value != null){
                resource = jedisPool.getResource();
                statusCode = resource.set(key, value);
            }else{
                logger.info("redis调用set方法没有成功,key:[" + key + "]" + "value:[" + value + "]");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("set失败,key:[" + key + "]" + "value:[" + value + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return statusCode;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time){
        String statusCode = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            statusCode = resource.set(key, value, nxxx, expx, time);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return statusCode;
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
        Jedis resource = null;
        try{
            if(key != null && value != null){
                resource = jedisPool.getResource();
                if(resource != null){
                    statusCode = resource.set(key.getBytes("UTF-8"), SerializeUtil.serialize(value), nxxx.getBytes("UTF-8"), expx.getBytes("UTF-8"), expireTime);
                }else{
                    logger.info("redis调用setObject方法没有成功,redis实例获取失败");
                }
            }else{
                logger.info("redis调用setObject方法没有成功,key:[" + key + "]" + "value:[" + value + "]");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("setObject失败,key:[" + key + "]" + "value:[" + value + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return statusCode;
    }

    @Override
    public String setObject(String key, Object value){
        String statusCode = null;
        Jedis resource = null;
        try{
            if(key != null && value != null){
                resource = jedisPool.getResource();
                if(resource != null){
                    statusCode = resource.set(key.getBytes("UTF-8"), SerializeUtil.serialize(value));
                }else{
                    logger.info("redis调用setObject方法没有成功,redis实例获取失败");
                }
            }else{
                logger.info("redis调用setObject方法没有成功,key:[" + key + "]" + "value:[" + value + "]");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("setObject失败,key:[" + key + "]" + "value:[" + value + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return statusCode;
    }

    @Override
    public String get(String key){
        String value = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            value = resource.get(key);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("get失败,key:[" + key + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return value;
    }

    @Override
    public Object getObject(String key){
        Object value = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            if(resource != null){
                value = SerializeUtil.unserialize(resource.get(key.getBytes("UTF-8")));
            }else{
                logger.info("redis调用getObject方法没有成功,redis实例获取失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("getObject失败,key:[" + key + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        //        logger.info("redis getObject :"+value);
        return value;
    }

    @Override
    public Set<String> getkeys(String key){
        Set<String> keySet = new HashSet<String>();
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            if(resource != null){
                keySet.addAll(resource.keys(key));
            }else{
                logger.info("redis调用keys方法没有成功,redis实例获取失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("getkeys失败,key:[" + key + "]");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return keySet;
    }

    public Long batchDel(String prefix){
        Long status = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            Set<String> set = resource.keys(prefix + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()){
                String keyStr = it.next();
                status = resource.del(keyStr);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("批量删除key失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return status;
    }

    @Override
    public Boolean exists(String key){
        boolean exists = false;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            exists = resource.exists(key);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return exists;
    }

    @Override
    public Long del(String key){
        Long status = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            status = resource.del(key);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return status;
    }

    @Override
    public Long del(String... keys){
        Long status = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            status = resource.del(keys);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return status;
    }

    /**
     * left顺序存储队列
     */
    public void lpush(String key, byte[] value){
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            resource.lpush(key.getBytes("UTF-8"), value);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //返还到连接池
            if(resource != null){
                resource.close();
            }
        }
    }

    /**
     *  right反向存储队列
     */
    public void rpush(String key, byte[] value){
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            resource.rpush(key.getBytes("UTF-8"), value);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //返还到连接池
            if(resource != null){
                resource.close();
            }
        }
    }

    /**
     * 获取队列数据
     */
    public List<byte[]> lrange(String key){
        List<byte[]> list = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            list = resource.lrange(key.getBytes("UTF-8"), 0, -1);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            if(resource != null){
                resource.close();
            }
        }
        return list;
    }

    /**
     * right反向获取队列数据
     */
    public byte[] lpop(String key){
        byte[] bytes = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            bytes = resource.lpop(key.getBytes("UTF-8"));
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //返还到连接池
            if(resource != null){
                resource.close();
            }
        }
        return bytes;
    }

    /**
     * left顺序获取队列数据
     */
    public byte[] rpop(String key){
        byte[] bytes = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            bytes = resource.rpop(key.getBytes("UTF-8"));
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //返还到连接池
            if(resource != null){
                resource.close();
            }
        }
        return bytes;
    }

    /**
     * left顺序获取队列数据
     */
    public List<byte[]> blpop(byte[]... keys){
        List<byte[]> result = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            result = resource.blpop(0, keys);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //返还到连接池
            if(resource != null){
                resource.close();
            }
        }
        return result;
    }

    @Override
    public Long incr(String key){
        Long cnt = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            cnt = resource.incr(key);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return cnt;
    }

    @Override
    public Long llen(String key){
        Long len = null;
        Jedis resource = null;
        try{
            resource = jedisPool.getResource();
            len = resource.llen(key);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("redis服务失败");
        }finally{
            //释放redis对象,返还到连接池  
            if(resource != null){
                resource.close();
            }
        }
        return len;
    }

}
