/**
 * 
 */
package com.bcbpm.service.log.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcbpm.dao.log.IRecordUserAccessLogDao;
import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.model.log.AccessLog;
import com.bcbpm.service.log.IRecordUserAccessLog;

/**<p>Title: RecordUserAccessLogImpl</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午6:17:16
 * @version :
 * @description:
 */
@Service("recordUserAccessLog")
public class RecordUserAccessLogImpl implements IRecordUserAccessLog{
    @Autowired
    private IRecordUserAccessLogDao recordUserAccessLogDao = null;

    @Override
    public void addBatchAccessLogs(List<AccessLog> accessLoglist){
        try{
            recordUserAccessLogDao.addBatchAccessLogs(accessLoglist);
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    /**
     * @Title: 根据条件查询日志 
     * @author jacky
     * @date 2018年10月19日 下午8:31:45
     * @param param
     * @return
     */
    public List<AccessLog> findAccessLogs(Map<String, Object> param){
        return recordUserAccessLogDao.findAccessLogs(param);
    }
}
