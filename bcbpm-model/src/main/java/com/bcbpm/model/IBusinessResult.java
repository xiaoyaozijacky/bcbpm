/**
 * 
 */
package com.bcbpm.model;

/**<p>Title: IBusinessResult</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午10:07:31
 * @version :
 * @description:
 */
public interface IBusinessResult{
    public Integer getCode();

    public String getMsg();

    public IBusinessResult setMsg(String... extInfo);
}
