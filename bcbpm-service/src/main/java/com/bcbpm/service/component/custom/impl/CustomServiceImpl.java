package com.bcbpm.service.component.custom.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.dao.custom.ICustomDao;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.model.custom.CustomForm;
import com.bcbpm.model.custom.CustomResultEnum;
import com.bcbpm.service.component.custom.ICustomService;

@Service("customService")
public class CustomServiceImpl implements ICustomService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICustomDao customDao = null;

    @Override
    public int insertCustomForm(CustomForm form){
        Integer rt = 0;
        try{
            rt = customDao.insertCustomForm(form);
        }catch(Exception e){
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