package com.bcbpm.controller.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.common.PageModel;
import com.bcbpm.model.custom.CustomForm;
import com.bcbpm.service.component.custom.ICustomService;

/**
 * <p>Title: CustomController</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午5:12:20
 * @version :
 * @description:
 */
@RestController
//@RequestMapping("/custom")
public class CustomController{
    Logger logger = LoggerFactory.getLogger(getClass());

    // 注入Service
    @Autowired
    private ICustomService customService = null;
    @Autowired
    private SessionDeal sessionDeal = null;
    @Autowired
    private HttpServletRequest request;

    //     根据id获取自定义表单
    @RequestMapping(value = "/getFormConfig/{id}", method = RequestMethod.GET, produces = "application/json")
    public CustomForm getFormConfig(@PathVariable String id){
        //        User user = (User) sessionDeal.getNowUserFront(request);
        CustomForm custom = customService.getFormConfig(id);
        return custom;
    }

    // 根据id获取自定义表单
    @RequestMapping(value = "/getFormList", method = RequestMethod.GET, produces = "application/json")
    public PageModel getFormList(String formName, String description, String formType, Integer currentPage, Integer pageSize){
        //        User user = (User) sessionDeal.getNowUserFront(request);
        //        String userId = user.getUserId();
        String userId = null;
        String tenantId = null;
        PageModel pm = new PageModel();
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
        pm.setPageSize(pageSize);
        pm.setCurrentPage(currentPage);
        List<CustomForm> customList = customService.findCustomForms(formName, description, formType, tenantId, userId, pm.getPage(), pageSize);
        Integer totalPageNum = customService.findCustomFormsCnt(formName, description, formType, tenantId, userId);

        pm.setTotalPageNum(totalPageNum);
        pm.setData(customList);
        return pm;
    }

    // 测试插入用户
    @RequestMapping("/insertUser")
    public Map<String, Object> insertUser(String userName, String note, Integer flg){
        //        User user = new User();
        //        user.setUserName(userName);
        //        // 结果会回填主键，返回插入条数
        //        Random random = new Random();
        //        int rd = random.nextInt(10);
        //        if(rd % 2 == 0){
        //            logger.info("rd等于：" + rd + "，目前使用从库");
        //            DatabaseContextHolder.setDatabaseType(DatabaseType.back);
        //        }else{
        //            logger.info("rd:等于：" + rd + "，目前使用主库");
        //            DatabaseContextHolder.setDatabaseType(DatabaseType.main);
        //        }
        //        int update = customService.insertUser(user);
        Map<String, Object> result = new HashMap<>();
        //        result.put("success", update == 1);
        //        result.put("user", user);
        return result;
    }

}