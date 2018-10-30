/**
 * 
 */
package com.bcbpm.framework.data.enums;

import com.bcbpm.model.IBusinessResult;

/**
* <p>Title: 全局异常信息定义</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月12日 下午5:43:34
 * @version :
 * @description:
 */
public enum ResultEnum implements IBusinessResult {
    // 后续根据业务类型进行扩展
    UNKONW_ERROR ( -1 , "未知错误" ) , SUCCESS ( 0 , "成功" ) , ERROR ( 1 , "失败" ) , LOGIN_ERROR1 ( 10001 , "用户[*]不存在" ) , LOGIN_ERROR2 ( 10002 , "登录密码不正确" ) , LOGIN_ERROR3 ( 10003 ,
            "当前用户:[*]已锁定" ) , LOGIN_ERROR4 ( 10004 , "产品使用期限已到，请联系客服！" );

    private Integer resultCode;
    private String resultMsg;

    // 
    ResultEnum(Integer code, String msg){
        this.resultCode = code;
        this.resultMsg = msg;
    }

    public Integer getResultCode(){
        return resultCode;
    }

    public String getResultMsg(){
        return resultMsg;
    }

    public ResultEnum setResultMsg(String... extInfo){
        for(String arg : extInfo){
            this.resultMsg = resultMsg.replace("*", arg);
        }
        return this;
    }
}