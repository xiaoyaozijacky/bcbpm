package com.bcbpm.model.log;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Title: AccessLog</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午3:17:32
 * @version :
 * @description: 访问记录
 */
public class AccessLog implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;//主键
    private String url;//访问地址
    private String ip;//访问IP
    private String controllerMethod;//组件方法名称
    @JsonIgnore
    private String[] paramNames;// 请求参数名称
    private String paramNamesView;
    @JsonIgnore
    private Object[] paramValues;// 请求参数值
    private String paramValuesView;
    @JsonIgnore
    private byte[] paramValuesBytes;
    private String returnName;// 返回类型名称
    @JsonIgnore
    private Object returnValue;// 返回值
    private String returnValueView;// 返回值
    @JsonIgnore
    private byte[] returnValueBytes;
    @JsonIgnore
    private Long startTimeMs;//进入时间
    @JsonIgnore
    private Long endTimeMs;//结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//进入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
    private Integer executeTime;//方法执行时间(单位：ms)
    private String userId;//访问人员
    private String userName;//访问人员
    private String tenantId;//租户标识
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;//创建时间
    private String exception;//记录异常信息
    @JsonIgnore
    private byte[] exceptionBytes;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

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

    public String getControllerMethod(){
        return controllerMethod;
    }

    public void setControllerMethod(String controllerMethod){
        this.controllerMethod = controllerMethod;
    }

    public String[] getParamNames(){
        return paramNames;
    }

    public void setParamNames(String[] paramNames){
        this.paramNames = paramNames;
    }

    public String getParamNamesView(){
        return paramNamesView;
    }

    public void setParamNamesView(String paramNamesView){
        this.paramNamesView = paramNamesView;
    }

    public Object[] getParamValues(){
        return paramValues;
    }

    public void setParamValues(Object[] paramValues){
        this.paramValues = paramValues;
    }

    public String getParamValuesView(){
        return paramValuesView;
    }

    public void setParamValuesView(String paramValuesView){
        this.paramValuesView = paramValuesView;
    }

    public byte[] getParamValuesBytes(){
        return paramValuesBytes;
    }

    public void setParamValuesBytes(byte[] paramValuesBytes){
        this.paramValuesBytes = paramValuesBytes;
        this.paramValuesView = JSON.toJSONString(JSON.parse(paramValuesBytes));
    }

    public String getReturnName(){
        return returnName;
    }

    public void setReturnName(String returnName){
        this.returnName = returnName;
    }

    public Object getReturnValue(){
        return returnValue;
    }

    public void setReturnValue(Object returnValue){
        this.returnValue = returnValue;
    }

    public String getReturnValueView(){
        return returnValueView;
    }

    public void setReturnValueView(String returnValueView){
        this.returnValueView = returnValueView;
    }

    public byte[] getReturnValueBytes(){
        return returnValueBytes;
    }

    public void setReturnValueBytes(byte[] returnValueBytes){
        this.returnValueBytes = returnValueBytes;
        this.returnValueView = JSON.toJSONString(JSON.parse(returnValueBytes));
    }

    public Long getStartTimeMs(){
        return startTimeMs;
    }

    public void setStartTimeMs(Long startTimeMs){
        this.startTimeMs = startTimeMs;
    }

    public Long getEndTimeMs(){
        return endTimeMs;
    }

    public void setEndTimeMs(Long endTimeMs){
        this.endTimeMs = endTimeMs;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
    }

    public Integer getExecuteTime(){
        return executeTime;
    }

    public void setExecuteTime(Integer executeTime){
        this.executeTime = executeTime;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getTenantId(){
        return tenantId;
    }

    public void setTenantId(String tenantId){
        this.tenantId = tenantId;
    }

    public Date getCreated(){
        return created;
    }

    public void setCreated(Date created){
        this.created = created;
    }

    public String getException(){
        return exception;
    }

    public void setException(String exception){
        this.exception = exception;
    }

    public byte[] getExceptionBytes(){
        return exceptionBytes;
    }

    public void setExceptionBytes(byte[] exceptionBytes){
        this.exceptionBytes = exceptionBytes;
        this.exception = JSON.toJSONString(JSON.parse(exceptionBytes));
    }

    @Override
    public String toString(){
        return "AccessLog{" + "\n url=" + url + ",\n ip=" + ip + ",\n controllerMethod=" + controllerMethod + ",\n paramNames=" + paramNamesView + ",\n paramValues=" + paramValuesView
                + ",\n returnName=" + returnName + ",\n returnValue=" + returnValueView +
                //            ", startTimeMs=" + startTimeMs +
                //            ", endTimeMs=" + endTimeMs+
                ",\n startTime=" + startTime + ",\n endTime=" + endTime + ",\n executeTime=" + executeTime + ",\n userId=" + userId + ",\n userName=" + userName + ",\n tenantId='" + tenantId
                + ",\n exception=" + exception + " \n }";
    }
}