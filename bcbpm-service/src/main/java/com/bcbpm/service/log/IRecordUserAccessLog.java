/**
 * 
 */
package com.bcbpm.service.log;

import java.util.List;
import java.util.Map;

import com.bcbpm.model.log.AccessLog;

/**<p>Title: IRecordUserAccessLog</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午5:59:56
 * @version :
 * @description: 记录用户日志到数据库中
 */
public interface IRecordUserAccessLog{
    /**
     * @Title: 日志入库
     * @author jacky
     * @date 2018年10月19日 下午6:14:24
     */
    public void addBatchAccessLogs(List<AccessLog> accessLoglist);

    /**
     * @Title: 根据条件查询日志 
     * @author jacky
     * @date 2018年10月19日 下午8:31:45
     * @param param
     * @return
     */
    public List<AccessLog> findAccessLogs(Map<String, Object> param);
}
