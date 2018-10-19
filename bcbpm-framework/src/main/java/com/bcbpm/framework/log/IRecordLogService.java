/**
 * 
 */
package com.bcbpm.framework.log;

/**<p>Title: IRecordLogService</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午5:39:27
 * @version :
 * @description:
 */
public interface IRecordLogService{
    /**
     * @Title: 正常记录 
     * @author jacky
     * @date 2018年10月19日 下午5:41:19
     * @param ret
     */
    public void recordLog(Object ret);

    /**
     * @Title: 异常记录 
     * @author jacky
     * @date 2018年10月19日 下午5:41:29
     * @param exception
     */
    public void recordLogException(String exception);
}
