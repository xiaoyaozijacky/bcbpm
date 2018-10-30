package com.bcbpm.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bcbpm.model.user.User;

/**
 * <p>Title: 自定义表单持久层</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:22:36
 * @version :
 * @description: 自定义表单持久层处理
 */
@Repository
public interface IUserDao{
    /**
     * @Title: 根据id获取用户信息 
    * @param @param id
    * @param @return   
    * @return User    
    * @author jacky
    * @date 2018年9月13日 上午9:41:06
     */
    User getUser(Long id);

    /**
     * @Title: 新增用户 
    * @param @param user
    * @param @return   
    * @return int    
    * @author jacky
    * @date 2018年9月13日 上午9:41:25
     */
    int insertUser(User user);

    /**
     * @Title: 根据条件模糊查询用户列表 
    * @param @param userName
    * @param @param note
    * @param @return   
    * @return List<User>    
    * @author jacky
    * @date 2018年9月13日 上午11:07:33
     */
    List<User> findUsers(@Param("userName") String userName, @Param("note") String note);
}