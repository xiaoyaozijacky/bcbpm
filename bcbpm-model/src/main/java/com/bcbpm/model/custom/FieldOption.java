package com.bcbpm.model.custom;

import java.io.Serializable;

/**<p>Title: 字段属性</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:27:58
 * @version :
 * @description:
 */
public class FieldOption implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//主键
    private PropertyConfig config;//属性定义
    private String defaultSetting;//字段默认值设置
    private String description;//属性描述
    private boolean required;//是否必填 true 1, false 0
    private boolean unique;//是否唯一 true 1, false 0

    private String formFieldId;//所属字段id

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public PropertyConfig getConfig(){
        return config;
    }

    public void setConfig(PropertyConfig config){
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

}
