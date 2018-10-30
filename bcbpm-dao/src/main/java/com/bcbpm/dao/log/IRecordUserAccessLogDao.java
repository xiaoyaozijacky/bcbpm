/**
 * 
 */
package com.bcbpm.dao.log;

import java.util.List;
import java.util.Map;

import com.bcbpm.model.log.AccessLog;

/**<p>Title: IRecordUserAccessLogDao</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午6:18:50
 * @version :
 * @description:
 */
public interface IRecordUserAccessLogDao{
    public void addBatchAccessLogs(List<AccessLog> accessLoglist);

    List<AccessLog> findAccessLogs(Map<String, Object> param);
}
