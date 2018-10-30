package com.bcbpm.dao.custom.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.bcbpm.model.custom.ConfigOption;
import com.bcbpm.model.custom.CustomForm;
import com.bcbpm.model.custom.FieldOption;
import com.bcbpm.model.custom.FormField;
import com.bcbpm.model.custom.OptionConfig;

/**
 * <p>Title: 动态sql</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月16日 下午4:01:47
 * @version :
 * @description:
 */
public class CustomDynaSqlProvider{

    //  新增自定义表单
    public String insertCustomForm(CustomForm customForm){
        return new SQL(){
            {
                INSERT_INTO("t_custom_form ");
                VALUES("id", "#{id}");
                VALUES("formName", "#{formName}");
                VALUES("created", "#{created}");
                VALUES("createdBy", "#{createdBy}");
                VALUES("lastUpdated", "#{lastUpdated}");
                VALUES("lastUpdatedBy", "#{lastUpdatedBy}");
                VALUES("description", "#{description}");
                VALUES("formType", "#{formType}");
                VALUES("tenantId", "#{tenantId}");
            }
        }.toString();
    }

    // 新增表单字段
    public String insertFormField(FormField formField){
        return new SQL(){
            {
                INSERT_INTO("t_custom_form_field ");
                VALUES("id", "#{id}");
                VALUES("type", "#{type}");
                VALUES("name", "#{name}");
                VALUES("icon", "#{icon}");
                VALUES("formField", "#{formField}");
                VALUES("created", "#{created}");
                VALUES("createdBy", "#{createdBy}");
                VALUES("formId", "#{formId}");
            }
        }.toString();
    }

    // 新增字段选项
    public String insertFieldOption(FieldOption fieldOption){
        return new SQL(){
            {
                INSERT_INTO("t_custom_field_option ");
                VALUES("id", "#{id}");
                VALUES("defaultSetting", "#{defaultSetting}");
                VALUES("defaultValue", "#{defaultValue}");
                VALUES("description", "#{description}");
                VALUES("required", "#{required}");
                VALUES("`unique`", "#{unique}");
                VALUES("created", "#{created}");
                VALUES("createdBy", "#{createdBy}");
                VALUES("formFieldId", "#{formFieldId}");
            }
        }.toString();
    }

    // 新增选项配置
    public String insertOptionConfig(OptionConfig optionConfig){
        return new SQL(){
            {
                INSERT_INTO("t_custom_option_config ");
                VALUES("id", "#{id}");
                VALUES("type", "#{type}");
                VALUES("fieldOptionId", "#{fieldOptionId}");
                VALUES("created", "#{created}");
                VALUES("createdBy", "#{createdBy}");
            }
        }.toString();
    }

    // 新增配置明细
    public String insertConfigOption(ConfigOption configOption){
        return new SQL(){
            {
                INSERT_INTO("t_custom_config_option ");
                VALUES("id", "#{id}");
                VALUES("title", "#{title}");
                VALUES("value", "#{value}");
                VALUES("def", "#{def}");
                VALUES("created", "#{created}");
                VALUES("createdBy", "#{createdBy}");
                VALUES("optionConfigId", "#{optionConfigId}");
            }
        }.toString();
    }

    public String updateCustomForm(CustomForm customForm){
        return new SQL(){
            {
                UPDATE("t_custom_form");
                if(customForm.getFormName() != null){
                    SET("formName = #{formName}");
                }
                if(customForm.getDescription() != null){
                    SET("description = #{description}");
                }
                if(customForm.getFormType() != null){
                    SET("formType = #{formType}");
                }
                //              if(customForm.getTenantId()!= null){
                //                 SET("tenantId = #{tenantId}");
                //              }
                SET("lastUpdated = #{lastUpdated}");
                SET("lastUpdatedBy = #{lastUpdatedBy}");
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

    public String updateFormField(FormField formField){
        return new SQL(){
            {
                UPDATE("t_custom_form_field ");
                if(formField.getType() != null){
                    SET("type = #{type}");
                }
                if(formField.getName() != null){
                    SET("name = #{name}");
                }
                if(formField.getIcon() != null){
                    SET("icon = #{icon}");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

    public String updateFieldOption(FieldOption fieldOption){
        return new SQL(){
            {
                UPDATE("t_custom_field_option ");
                if(fieldOption.getDefaultSetting() != null){
                    SET("defaultSetting = #{defaultSetting}");
                }
                if(fieldOption.getDefaultValue() != null){
                    SET("defaultValue = #{defaultValue}");
                }
                if(fieldOption.getDescription() != null){
                    SET("description = #{description}");
                }
                SET("required = #{required}");
                SET("`unique` = #{unique}");
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

    public String updateOptionConfig(OptionConfig optionConfig){
        return new SQL(){
            {
                UPDATE("t_custom_option_config ");
                if(optionConfig.getType() != null){
                    SET("type = #{type}");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

    public String updateConfigOption(ConfigOption configOption){
        return new SQL(){
            {
                UPDATE("t_custom_config_option ");
                if(configOption.getTitle() != null){
                    SET("title = #{title}");
                }
                if(configOption.getValue() != null){
                    SET("value = #{value}");
                }
                SET("def = #{def}");
                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}