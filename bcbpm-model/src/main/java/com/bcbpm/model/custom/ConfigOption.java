package com.bcbpm.model.custom;

import java.io.Serializable;

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

    private String propertyConfigId;//所属字段属性id

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

    public String getPropertyConfigId(){
        return propertyConfigId;
    }

    public void setPropertyConfigId(String propertyConfigId){
        this.propertyConfigId = propertyConfigId;
    }

}
