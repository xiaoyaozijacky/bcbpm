package com.bcbpm.service.user;

import java.util.List;

import com.bcbpm.model.user.User;

public interface IUserService{
    // 获取用户信息
    public User getUser(Long id);

    // 新增用户
    public int insertUser(User user);

    List<User> findUsers(String userName, String note);
}
