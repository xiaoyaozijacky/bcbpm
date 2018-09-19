/**
 * 
 */
package com.bcbpm.framework.data.access;

/**<p>Title: 返回客户端的数据结构体</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午8:44:10
 * @version :
 * @description:
 */
public class BaseResponse{
    private Integer code = 0;//编码
    private String msg = "操作成功";//信息
    private Object body = null;//内容

    public BaseResponse(){
    }

    public BaseResponse(Object body){
        this.body = body;
    }

    public BaseResponse(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Object getBody(){
        return body;
    }

    public void setBody(Object body){
        this.body = body;
    }

}
