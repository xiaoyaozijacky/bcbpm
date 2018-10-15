package com.bcbpm.dao.custom;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.bcbpm.model.custom.CustomForm;

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
    int insertCustomForm(CustomForm form);

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
}