/**
 * 
 */
package com.bcbpm.model.custom;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**<p>Title: OptionConfig</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午3:22:45
 * @version :
 * @description:
 */
public class OptionConfig implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//主键
    private String type;// 属性类型 ：input single
    private List<ConfigOption> options;//字段预选值，例如：select,radio,checkbox等
    private String fieldOptionId;//所属字段属性id
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

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public List<ConfigOption> getOptions(){
        return options;
    }

    public void setOptions(List<ConfigOption> options){
        this.options = options;
    }

    public String getFieldOptionId(){
        return fieldOptionId;
    }

    public void setFieldOptionId(String fieldOptionId){
        this.fieldOptionId = fieldOptionId;
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
