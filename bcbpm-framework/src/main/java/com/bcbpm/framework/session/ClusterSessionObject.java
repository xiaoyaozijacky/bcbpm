package com.bcbpm.framework.session;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集群缓存对象
 * */
public class ClusterSessionObject implements Serializable{
	private static final long serialVersionUID = 1L;
	// 缓存对象
	private Map<String,Object> attribute=new ConcurrentHashMap<String,Object>();
	public Object getAttribute(String key) {
		return attribute.get(key);
	}
	public void setAttribute(String key, Object obj) {
		this.attribute.put(key, obj);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
