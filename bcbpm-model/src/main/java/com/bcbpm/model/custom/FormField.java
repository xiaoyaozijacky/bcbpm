package com.bcbpm.model.custom;

import java.io.Serializable;

/**<p>Title: 表单字段</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:27:58
 * @version :
 * @description:
 */
public class FormField implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;//字段主键
    private String type;//字段类型  text：文本   category：选项
    private String name;//字段名称
    private String icon;//图标
    private String formField;// 表单字段标识 由type+Id组合而成
    private FieldOption options;//字段属性

    private String formId;//所属表单id

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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getFormField(){
        return formField;
    }

    public void setFormField(String formField){
        this.formField = formField;
    }

    public FieldOption getOptions(){
        return options;
    }

    public void setOptions(FieldOption options){
        this.options = options;
    }

    public String getFormId(){
        return formId;
    }

    public void setFormId(String formId){
        this.formId = formId;
    }
}
