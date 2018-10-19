package com.bcbpm.framework.interceptor;

import java.io.Serializable;

public class StatisticsVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private String url;//访问地址
    private String ip;//访问IP
    private String controllerName;//组件名称
    private String methodName;//方法
    private String[] paramNames;//参数名称
    private Object[] paramValues;//参数值
    private long maxExcuteTime;//最大执行时间
    private long clickTimes;//点击次数
    //    private List<Object> reqArgs;//发生最大执行时间时入参（新）
    //    private String requestParams;//发生最大执行时间时入参（老）
    private String accessTime;//方法执行时间
    private String userId;//访问人员

    private String tenantId;//租户标识
    private String id;//主键

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getControllerName(){
        return controllerName;
    }

    public void setControllerName(String controllerName){
        this.controllerName = controllerName;
    }

    public String getMethodName(){
        return methodName;
    }

    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    public String[] getParamNames(){
        return paramNames;
    }

    public void setParamNames(String[] paramNames){
        this.paramNames = paramNames;
    }

    public Object[] getParamValues(){
        return paramValues;
    }

    public void setParamValues(Object[] paramValues){
        this.paramValues = paramValues;
    }

    public long getMaxExcuteTime(){
        return maxExcuteTime;
    }

    public void setMaxExcuteTime(long maxExcuteTime){
        this.maxExcuteTime = maxExcuteTime;
    }

    public long getClickTimes(){
        return clickTimes;
    }

    public void setClickTimes(long clickTimes){
        this.clickTimes = clickTimes;
    }

    public String getAccessTime(){
        return accessTime;
    }

    public void setAccessTime(String accessTime){
        this.accessTime = accessTime;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getTenantId(){
        return tenantId;
    }

    public void setTenantId(String tenantId){
        this.tenantId = tenantId;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}