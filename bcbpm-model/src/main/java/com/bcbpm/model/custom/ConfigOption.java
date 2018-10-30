package com.bcbpm.model.custom;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**<p>Title: 字段属性</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:27:58
 * @version :
 * @description: 字段预选值，例如：select,radio,checkbox等
 */
public class ConfigOption implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//属性主键
    private String title;//属性名称
    private String value;//属性值
    private boolean def;// 是否默认项  true 1, false 0

    @JsonIgnore
    private String optionConfigId;//所属选项配置id
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

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public boolean isDef(){
        return def;
    }

    public void setDef(boolean def){
        this.def = def;
    }

    public String getOptionConfigId(){
        return optionConfigId;
    }

    public void setOptionConfigId(String optionConfigId){
        this.optionConfigId = optionConfigId;
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
}
