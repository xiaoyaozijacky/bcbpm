package com.bcbpm.service.component.custom;

import java.util.List;

import com.bcbpm.model.custom.CustomForm;

/**
 * <p>Title: ICustomService</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午4:48:25
 * @version :
 * @description: 用户自定义服务组件
 */
public interface ICustomService{
    /**
     * @Title: 根据id获取自定义表单信息  
     * @author jacky
     * @date 2018年10月15日 下午4:28:19
     * @param id
     * @return
     */
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
     * @return
     */
    List<CustomForm> findCustomForms(String formName, String description, String formType, String tenantId, String userId, Integer currentPage, Integer pageSize);

    Integer findCustomFormsCnt(String formName, String description, String formType, String tenantId, String userId);
}
