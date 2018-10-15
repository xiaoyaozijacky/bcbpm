/**
 * 
 */
package com.bcbpm.model.user;

import com.bcbpm.model.IBusinessResult;

/**<p>Title: UserResultEnum</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午9:59:00
 * @version :
 * @description:
 */
public enum UserResultEnum implements IBusinessResult {
    // 后续根据业务类型进行扩展
    SAVE_USER_ERROR ( 20001 , "保存用户失败" ) , DELETE_USER_ERROR ( 20002 , "删除用户失败" );

    private Integer code;
    private String msg;

    UserResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getResultCode(){
        return code;
    }

    public String getResultMsg(){
        return msg;
    }

    public UserResultEnum setResultMsg(String... extInfo){
        for(String arg : extInfo){
            this.msg = msg.replace("*", arg);
        }
        return this;
    }
}
