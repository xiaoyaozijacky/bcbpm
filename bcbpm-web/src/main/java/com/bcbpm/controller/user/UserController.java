package com.bcbpm.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcbpm.framework.data.db.DatabaseContextHolder;
import com.bcbpm.framework.data.enums.DatabaseType;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.user.User;
import com.bcbpm.service.user.IUserService;

/**
 * <p>Title: UserController</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 上午9:18:16
 * @version :
 * @description:
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController{
    Logger logger = LoggerFactory.getLogger(getClass());

    // 注入Service
    @Autowired
    private IUserService iUserService = null;
    @Autowired
    private SessionDeal sessionDeal = null;
    @Autowired
    private HttpServletRequest request;

    // 测试获取用户
    @RequestMapping("/getUser")
    public User getUser(Long id){
        return iUserService.getUser(id);
    }

    // 测试获取用户
    @RequestMapping("/getSessionUser")
    public User getSessionUser(){
        User user = (User) sessionDeal.getNowUserFront(request);
        return user;
    }

    // 测试插入用户
    @RequestMapping("/insertUser")
    public Map<String, Object> insertUser(String userName, String note, Integer flg){
        User user = new User();
        user.setUserName(userName);
        // 结果会回填主键，返回插入条数
        Random random = new Random();
        int rd = random.nextInt(10);
        if(rd % 2 == 0){
            logger.info("rd等于：" + rd + "，目前使用从库");
            DatabaseContextHolder.setDatabaseType(DatabaseType.back);
        }else{
            logger.info("rd:等于：" + rd + "，目前使用主库");
            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        }
        int update = iUserService.insertUser(user);
        Map<String, Object> result = new HashMap<>();
        result.put("success", update == 1);
        result.put("user", user);
        return result;
    }

    @RequestMapping("/list")
    public List<User> list(String userName, String note){
        // 访问模型层得到数据
        return iUserService.findUsers(userName, note);
    }
}