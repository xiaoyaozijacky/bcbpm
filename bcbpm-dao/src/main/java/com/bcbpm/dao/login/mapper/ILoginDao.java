package com.bcbpm.dao.login.mapper;

import org.springframework.stereotype.Repository;

import com.bcbpm.model.user.User;

@Repository
public interface ILoginDao{
    /**
     * @Title: 检查用户是否存在  
     * @author jacky
     * @date 2018年9月17日 下午8:56:04
     * @param input 用户名，手机号，邮箱三者其中之一均可
     * @return
     */
    public User checkUserIfexist(String input);

    /**
     * @Title: 根据输入查询用户
     * @author jacky
     * @date 2018年9月17日 下午8:56:04
     * @param input 用户名，手机号，邮箱三者其中之一均可
     * @param pwd 密码
     * @return
     */
    public User getUserInfo(String input, String pwd);
}