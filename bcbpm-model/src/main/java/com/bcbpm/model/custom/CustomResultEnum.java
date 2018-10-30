/**
 * 
 */
package com.bcbpm.model.custom;

import com.bcbpm.model.IBusinessResult;

/**<p>Title: UserResultEnum</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午9:59:00
 * @version :
 * @description:
 */
public enum CustomResultEnum implements IBusinessResult {
    // 后续根据业务类型进行扩展
    SAVE_CUSTOM_ERROR ( 30001 , "保存自定义表单失败" ) , DELETE_CUSTOM_ERROR ( 30002 , "删除自定义表单失败" );

    private Integer code;
    private String msg;

    CustomResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getResultCode(){
        return code;
    }

    public String getResultMsg(){
        return msg;
    }

    public CustomResultEnum setResultMsg(String... extInfo){
        for(String arg : extInfo){
            this.msg = msg.replace("*", arg);
        }
        return this;
    }
}
