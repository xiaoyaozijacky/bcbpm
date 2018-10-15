package com.bcbpm.service.login.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.dao.login.mapper.ILoginDao;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.data.enums.ResultEnum;
import com.bcbpm.model.user.User;
import com.bcbpm.service.login.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ILoginDao loginDao = null;

    /**
     * 登录逻辑
     */
    public User login(String input, String pwd){
        User user = null;
        // 1.校验当前用户是否存在
        user = loginDao.checkUserIfexist(input);
        if(user == null){
            logger.info(ResultEnum.LOGIN_ERROR1.setResultMsg(input).getResultMsg());
            throw new BusinessException(ResultEnum.LOGIN_ERROR1.setResultMsg(input));
        }else{
            // 2.校验密码是否正确
            user = loginDao.getUserInfo(input, pwd);
            if(user == null){
                logger.info(ResultEnum.LOGIN_ERROR2.getResultMsg());
                throw new BusinessException(ResultEnum.LOGIN_ERROR2);
            }else{
                // 3.校验用户是否被锁定
                if(user.getUserState() == 1){
                    logger.info(ResultEnum.LOGIN_ERROR3.setResultMsg(input).getResultMsg());
                    throw new BusinessException(ResultEnum.LOGIN_ERROR3.setResultMsg(input));
                }else{
                    // 4.校验用户是否在产品使用有效期内
                    Date expirationDate = (Date) user.getExpirationDate();//企业号到期日期
                    Date now = new Date();
                    boolean flag = expirationDate.before(now);
                    if(expirationDate == null || flag){//早于今天说明到期了
                        logger.info(ResultEnum.LOGIN_ERROR4.getResultMsg());
                        throw new BusinessException(ResultEnum.LOGIN_ERROR4);
                    }else{
                        logger.info("登录成功");
                        return user;
                    }
                }
            }
        }
    }
}