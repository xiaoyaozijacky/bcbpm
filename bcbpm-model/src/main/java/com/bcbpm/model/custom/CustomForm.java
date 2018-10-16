package com.bcbpm.model.custom;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**<p>Title: 自定义表单</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:27:58
 * @version :
 * @description:
 */
public class CustomForm implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//自定义表单主键
    private String formName;//自定义表单名称
    private List<FormField> fieldsList;//自定义表单字段组合
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;//表单创建时间
    private String createdBy;//表单创建人
    @JsonIgnore
    protected Date lastUpdated;//最新修改时间
    @JsonIgnore
    private String lastUpdatedBy;//最新修改人
    private String description;//表单描述

    private String formType;//表单类型 (根据业务进行分组，不同表单归属不同的业务分组下)
    private String tenantId;//租户标识

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFormName(){
        return formName;
    }

    public void setFormName(String formName){
        this.formName = formName;
    }

    public List<FormField> getFieldsList(){
        return fieldsList;
    }

    public void setFieldsList(List<FormField> fieldsList){
        this.fieldsList = fieldsList;
    }

    public Date getCreated(){
        return created;
    }

    public void setCreated(Date created){
        this.created = created;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public Date getLastUpdated(){
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getFormType(){
        return formType;
    }

    public void setFormType(String formType){
        this.formType = formType;
    }

    public String getTenantId(){
        return tenantId;
    }

    public void setTenantId(String tenantId){
        this.tenantId = tenantId;
    }
}
