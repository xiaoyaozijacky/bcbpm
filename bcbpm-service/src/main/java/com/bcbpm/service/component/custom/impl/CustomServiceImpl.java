package com.bcbpm.service.component.custom.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.dao.custom.ICustomDao;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.model.custom.ConfigOption;
import com.bcbpm.model.custom.CustomForm;
import com.bcbpm.model.custom.CustomResultEnum;
import com.bcbpm.model.custom.FieldOption;
import com.bcbpm.model.custom.FormField;
import com.bcbpm.model.custom.OptionConfig;
import com.bcbpm.model.user.User;
import com.bcbpm.service.component.custom.ICustomService;
import com.bcbpm.util.UuidUtil;

@Service("customService")
public class CustomServiceImpl implements ICustomService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICustomDao customDao = null;

    @Override
    public int saveCustomForm(CustomForm form, User user){
        Integer rt = 0;
        FieldOption fieldOption = null;
        OptionConfig optionConfig = null;

        try{
            form.setLastUpdated(Calendar.getInstance().getTime());
            form.setLastUpdatedBy(user.getUserId());
            form.setTenantId(user.getTenantId());
            if(StringUtils.isEmpty(form.getId())){
                // 新增自定义表单
                form.setId(UuidUtil.newGeneratId());
                form.setCreatedBy(user.getUserId());
                form.setCreated(Calendar.getInstance().getTime());
                customDao.insertCustomForm(form);

                if(form.getFieldsList() != null){
                    for(FormField formField : form.getFieldsList()){
                        //新增表单字段
                        formField.setId(UuidUtil.newGeneratId());
                        formField.setCreatedBy(user.getUserId());
                        formField.setCreated(Calendar.getInstance().getTime());
                        formField.setFormId(form.getId());
                        formField.setFormField(formField.getType() + "_" + form.getId());
                        customDao.insertFormField(formField);

                        // 新增字段选项
                        fieldOption = formField.getOptions();
                        fieldOption.setId(UuidUtil.newGeneratId());
                        fieldOption.setFormFieldId(formField.getId());
                        fieldOption.setCreatedBy(user.getUserId());
                        fieldOption.setCreated(Calendar.getInstance().getTime());
                        customDao.insertFieldOption(fieldOption);

                        // 新增选项配置
                        optionConfig = fieldOption.getConfig();
                        optionConfig.setId(UuidUtil.newGeneratId());
                        optionConfig.setFieldOptionId(fieldOption.getId());
                        optionConfig.setCreatedBy(user.getUserId());
                        optionConfig.setCreated(Calendar.getInstance().getTime());
                        customDao.insertOptionConfig(optionConfig);

                        // 新增配置明细
                        if(optionConfig.getOptions() != null){
                            for(ConfigOption configOption : optionConfig.getOptions()){
                                configOption.setId(UuidUtil.newGeneratId());
                                configOption.setOptionConfigId(optionConfig.getId());
                                configOption.setCreatedBy(user.getUserId());
                                configOption.setCreated(Calendar.getInstance().getTime());
                                customDao.insertConfigOption(configOption);
                            }
                        }
                    }
                }
            }else{
                // 做保存处理
                form.setLastUpdatedBy(user.getUserId());
                form.setTenantId(user.getTenantId());
                //                rt = customDao.saveCustomForm(form);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(CustomResultEnum.SAVE_CUSTOM_ERROR);
        }
        return rt;
    }

    @Override
    public CustomForm getFormConfig(String id){
        //        if(id == 1){
        //            DatabaseContextHolder.setDatabaseType(DatabaseType.back);
        //        }else{
        //            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        //        }
        //        Map map = customDao.getForm(id);
        return customDao.getFormConfig(id);
    }

    @Override
    public List<CustomForm> findCustomForms(String formName, String description, String formType, String tenantId, String userId, Integer currentPage, Integer pageSize){
        return customDao.findCustomForms(formName, description, formType, tenantId, userId, currentPage, pageSize);
    }

    @Override
    public Integer findCustomFormsCnt(String formName, String description, String formType, String tenantId, String userId){
        return customDao.findCustomFormsCnt(formName, description, formType, tenantId, userId);
    }

}