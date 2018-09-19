package com.bcbpm.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.user.User;
import com.bcbpm.service.login.ILoginService;

@Controller
//@RequestMapping("/")
@ResponseBody
public class LoginController{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ILoginService loginService = null;
    @Autowired
    private SessionDeal sessionDeal = null;

    @RequestMapping("/login")
    public User login(String input, String pwd){
        //        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = loginService.login(input, pwd);
        sessionDeal.loginFront(request, user);
        return user;
    }

}
