package com.bcbpm.service.login;

import com.bcbpm.model.user.User;

public interface ILoginService{
    /**
     * @Title: 用户登录 
     * @param input   用户名，手机号，邮箱三者其中之一均可
     * @param pwd MD5处理后的密码
    * @return ResultEnum    SUCCESS ( 0 , "成功" ) , ERROR ( 1 , "失败" ) , LOGIN_ERROR1 ( 10001 , "登录失败" ) , LOGIN_ERROR2 ( 10002 , "用户[*]不存在" ) , 
    * LOGIN_ERROR3 ( 10003 ,"登录密码不正确" ) , LOGIN_ERROR4 ( 10004 , "当前用户:[*]已锁定" ) , LOGIN_ERROR5 ( 10005 , "产品使用期限已到，请联系客服！" );
    * @author jacky
    * @date 2018年9月17日 下午8:27:25
     */
    public User login(String userId, String pwd);
}
