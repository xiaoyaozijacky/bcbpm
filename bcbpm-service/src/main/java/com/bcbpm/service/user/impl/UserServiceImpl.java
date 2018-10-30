package com.bcbpm.service.user.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.dao.user.IUserDao;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.data.db.DatabaseContextHolder;
import com.bcbpm.framework.data.enums.DatabaseType;
import com.bcbpm.model.user.User;
import com.bcbpm.model.user.UserResultEnum;
import com.bcbpm.service.user.IUserService;

@Service
public class UserServiceImpl implements IUserService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserDao userDao = null;

    @Override
    //    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1)
    public int insertUser(User user){
        Integer rt = 0;
        try{
            if(user.getIsEnterprise() == null){
                rt = userDao.insertUser(user);
            }else{
                rt = userDao.insertUser(user);
                rt = 5 / 0;
            }
        }catch(Exception e){
            throw new BusinessException(UserResultEnum.SAVE_USER_ERROR);
        }
        return rt;
    }

    @Override
    public User getUser(Long id){
        if(id == 1){
            DatabaseContextHolder.setDatabaseType(DatabaseType.back);
        }else{
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        }
        return userDao.getUser(id);
    }

    @Override
    public List<User> findUsers(String userName, String note){
        return userDao.findUsers(userName, note);
    }
}