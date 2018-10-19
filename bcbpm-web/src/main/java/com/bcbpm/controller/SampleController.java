package com.bcbpm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.framework.data.enums.ResultEnum;
import com.bcbpm.framework.data.redis.RedisClient;
import com.bcbpm.util.TimeUtil;

@Controller
@RequestMapping("/home")
@ResponseBody
public class SampleController{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisClient redisClient;

    @Value("${student.name}")
    private String name;
    @Value("${student.state}")
    private String state;

    @RequestMapping(value = "/now")
    public String getNow(){
        String now = TimeUtil.getDateTime();
        logger.info("正常访问controller的now方法，当前时间为：" + now);
        return "当前时间为：" + now;
    }

    @RequestMapping("/testInt")
    public Integer testSysError(){
        Integer i = 5;
        return i;
    }

    @RequestMapping("/testVoid")
    public void testVoid(String tt){
    }

    @RequestMapping("/testBusiError")
    public Integer testBusiError() throws Exception{
        logger.info("正常访问controller的testBusiError方法");
        int i = 0;
        try{
            i = 5 / 0;
        }catch(Exception e){
            throw new BusinessException(ResultEnum.LOGIN_ERROR2);
        }
        return i;
    }

    @RequestMapping("/login")
    public void login(String userName, String password){
        redisClient.getClient().set(userName, password);
    }

    @RequestMapping("/getInfo")
    public String getInfo(String userName, String password){
        return redisClient.getClient().get(userName);
    }
}
