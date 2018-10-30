package com.bcbpm.model.custom;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**<p>Title: 字段选项</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:27:58
 * @version :
 * @description:
 */
public class FieldOption implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//主键
    private OptionConfig config;//选项定义
    private String defaultSetting;//字段默认值设置
    private String defaultValue;//字段默认值
    private String description;//属性描述
    private boolean required;//是否必填 true 1, false 0
    private boolean unique;//是否唯一 true 1, false 0

    @JsonIgnore
    private String formFieldId;//所属字段id
    @JsonIgnore
    private Date created;//创建时间
    @JsonIgnore
    private String createdBy;//创建人

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public OptionConfig getConfig(){
        return config;
    }

    public void setConfig(OptionConfig config){
        this.config = config;
    }

    public String getDefaultSetting(){
        return defaultSetting;
    }

    public void setDefaultSetting(String defaultSetting){
        this.defaultSetting = defaultSetting;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public boolean isRequired(){
        return required;
    }

    public void setRequired(boolean required){
        this.required = required;
    }

    public boolean isUnique(){
        return unique;
    }

    public void setUnique(boolean unique){
        this.unique = unique;
    }

    public String getFormFieldId(){
        return formFieldId;
    }

    public void setFormFieldId(String formFieldId){
        this.formFieldId = formFieldId;
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

    public String getDefaultValue(){
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue){
        this.defaultValue = defaultValue;
    }
}
