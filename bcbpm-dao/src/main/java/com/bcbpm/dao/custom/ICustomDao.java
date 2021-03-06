package com.bcbpm.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.bcbpm.dao.custom.mapper.CustomDynaSqlProvider;
import com.bcbpm.model.custom.ConfigOption;
import com.bcbpm.model.custom.CustomForm;
import com.bcbpm.model.custom.FieldOption;
import com.bcbpm.model.custom.FormField;
import com.bcbpm.model.custom.OptionConfig;

/**
 * <p>Title: 自定义表单持久层</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:22:36
 * @version :
 * @description: 自定义表单持久层处理
 */
@Repository
public interface ICustomDao{
    /**
     * @Title: 根据id获取自定义表单信息  
     * @author jacky
     * @date 2018年10月15日 下午4:28:19
     * @param id
     * @return
     */
    //    @Select(" select cform.* from t_custom_form cform  where cform.id=#{id} ")
    CustomForm getFormConfig(String id);

    /**
     * @Title: 新增自定义表单 
     * @author jacky
     * @date 2018年10月15日 下午4:28:08
     * @param form
     * @return
     */
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "insertCustomForm")
    int insertCustomForm(CustomForm form);

    // 新增表单字段
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "insertFormField")
    int insertFormField(FormField formField);

    // 新增字段选项
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "insertFieldOption")
    int insertFieldOption(FieldOption fieldOption);

    // 新增选项配置
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "insertOptionConfig")
    int insertOptionConfig(OptionConfig optionConfig);

    // 新增配置明细
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "insertConfigOption")
    int insertConfigOption(ConfigOption configOption);

    // 动态更新自定义表单表
    @UpdateProvider(type = CustomDynaSqlProvider.class, method = "updateCustomForm")
    int updateCustomForm(CustomForm customForm);

    // 新增表单字段
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "updateFormField")
    int updateFormField(FormField formField);

    // 新增字段选项
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "updateFieldOption")
    int updateFieldOption(FieldOption fieldOption);

    // 新增选项配置
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "updateOptionConfig")
    int updateOptionConfig(OptionConfig optionConfig);

    // 新增配置明细
    @InsertProvider(type = CustomDynaSqlProvider.class, method = "updateConfigOption")
    int updateConfigOption(ConfigOption configOption);

    /**
     * @Title: 根据条件查询自定义表单数据 
     * @author jacky
     * @date 2018年10月15日 下午4:31:00
     * @param formName
     * @param description
     * @param formType
     * @param tenantId
     * @param userId
     * @return
     */
    @Select(" select cform.* from t_custom_form cform limit #{currentPage},#{pageSize} ")
    List<CustomForm> findCustomForms(String formName, String description, String formType, String tenantId, String userId, Integer currentPage, Integer pageSize);

    @Select(" select count(*)cnt from t_custom_form cform ")
    Integer findCustomFormsCnt(String formName, String description, String formType, String tenantId, String userId);

    // 删除自定义表单表
    @Delete(" delete from t_custom_form where id =#{id} ")
    int deleteCustomForm(String id);

    // 删除表单字段
    @Delete(" delete from t_custom_form_field where formId =#{formId} ")
    int deleteFormField(String formId);

    // 删除字段选项
    @Delete(" delete from t_custom_field_option where formFieldId =#{formFieldId} ")
    int deleteFieldOption(String formFieldId);

    // 删除选项配置
    @Delete(" delete from t_custom_option_config where fieldOptionId =#{fieldOptionId} ")
    int deleteOptionConfig(String fieldOptionId);

    // 删除配置明细
    @Delete(" delete from t_custom_config_option where optionConfigId =#{optionConfigId} ")
    int deleteConfigOption(String optionConfigId);

}