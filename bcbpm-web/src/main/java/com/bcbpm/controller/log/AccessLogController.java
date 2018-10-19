package com.bcbpm.controller.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcbpm.model.log.AccessLog;
import com.bcbpm.service.log.IRecordUserAccessLog;

/**
 * <p>Title: 访问日志处理</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 上午9:18:16
 * @version :
 * @description:
 */
@Controller
@ResponseBody
public class AccessLogController{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IRecordUserAccessLog recordUserAccessLog = null;

    @RequestMapping(value = "/accessLogs", method = RequestMethod.GET, produces = "application/json")
    public List<AccessLog> list(String url, String userId){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("url", url);
        param.put("userId", userId);
        return recordUserAccessLog.findAccessLogs(param);
    }
}