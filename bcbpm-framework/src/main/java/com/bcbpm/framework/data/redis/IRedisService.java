package com.bcbpm.framework.data.redis;

import java.util.List;
import java.util.Set;

/**
 * redis服务组件，包括了redis的各种操作
 * */
public interface IRedisService{

    /**
     * 放入redis缓存数据
     * Set the string value as value of the key. 
     * The string can't be longer than 1073741824 bytes (1 GB). 
     * @param key
     * @param value
     * @return  Status code reply
     * */
    public String set(String key, String value);

    /**
     * 放入redis缓存数据
     * 可以将业务对象序列化存储，取出时可以反序列化
     * @param key
     * @param value
     * @return  Status code reply
     * */
    public String setObject(String key, Object value);

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
    public String setObject(String key, Object value, String nxxx, String expx, long expireTime);

    /**
     * 放入redis缓存数据（补充扩展参数）
     * @param key
     * @param value
     * @param nxxx - NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist
     * @param expx - EX|PX, expire time units: EX = seconds; PX = milliseconds
     * @param time - expire time in the units of expx
     * @return  Status code reply
     * */
    public String set(String key, String value, String nxxx, String expx, long time);

    /**
     * 取得redis缓存数据
     * Get the value of the specified key. If the key does not exist null is returned. If the value stored at key is not a string an error is returned because GET can only handle string values. 
     * */
    public String get(String key);

    /**
     * 取得redis缓存数据(用于取得对象序列化值)
     * Get the value of the specified key. If the key does not exist null is returned. If the value stored at key is not a string an error is returned because GET can only handle string values. 
     * */
    public Object getObject(String key);

    /**
     * key:带通配符的所有缓存key
     * 
     */
    public Set<String> getkeys(String key);

    /**
     * 判断是否存在key
     * */
    public Boolean exists(String key);

    /**
     * 删除key(K/V整体)
     * 
     * */
    public Long del(String key);

    /**
     * 批量删除key值
     * Remove the specified keys. If a given key does not exist no operation is performed for this key. The command returns the number of keys removed. 
     * @return Integer reply, specifically: an integer greater than 0 if one or more keys were removed 0 if none of the specified key existed
     * */
    public Long del(String... keys);

    /**
     * left顺序(从左到右)存入队列数据
     * */
    public void lpush(String key, byte[] value);

    /**
     * right顺序(从右到左)存入队列数据
     * */
    public void rpush(String key, byte[] value);

    /**
     * left顺序(从左到右)取出队列数据
     * */
    public byte[] lpop(String key);

    /**
     * right顺序(从右到左)取出队列数据
     * */
    public byte[] rpop(String key);

    /**
     * 阻塞取回队列数据
     * */
    public List<byte[]> blpop(byte[]... keys);

    /**
     * 取回队列所有数据
     * */
    public List<byte[]> lrange(String key);

    /**
     * 取回队列数据长度 
     * */
    public Long llen(String key);

    //	jedis.hdel("user","age");//删除map中的某个键值 
    //	jedis.hmget("user", "age"); //因为删除了，所以返回的是null  
    //	jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
    //	jedis.exists("user"));//是否存在key为user的记录 返回true  
    //	jedis.hkeys("user"));//返回map对象中的所有key  
    //	jedis.hvals("user"));//返回map对象中的所有value 

    /**
     * 对指定key值的数据加1，返回当前总数
     * */
    public Long incr(String key);

    /**
     * 
     *
     * @Author:       [wpp]     
     * @Company:      [益联云]
     * @Version:      [v8.5]           
     * @CreateDate:   [2018年6月20日 下午2:14:25]  
     * @Param:        [prefix 需要批量删除key的前缀]
     * @return:       [Long]
     * @Description:  [批量删除key]
     *
     */
    public Long batchDel(String prefix);
}
