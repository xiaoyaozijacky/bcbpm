package com.bcbpm.framework.data.access;

import com.bcbpm.model.IBusinessResult;

/**
* <p>Title: 业务异常处理</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月12日 下午5:32:23
 * @version :
 * @description: 异常基础类
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BusinessException(){
        super();
    }

    private Integer code; //错误码

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(Throwable cause){
        super(cause);
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public BusinessException(IBusinessResult resultEnum){
        super(resultEnum.getResultMsg());
        this.code = resultEnum.getResultCode();
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

}
