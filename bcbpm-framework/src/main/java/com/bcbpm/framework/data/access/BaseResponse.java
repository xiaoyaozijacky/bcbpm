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
    private Integer resultCode = 0;//编码
    private String resultMsg = "操作成功";//信息
    private Object data = null;//内容

    public BaseResponse(){
    }

    public BaseResponse(Object body){
        this.data = body;
    }

    public BaseResponse(Integer code, String msg){
        this.resultCode = code;
        this.resultMsg = msg;
    }

    public Integer getResultCode(){
        return resultCode;
    }

    public void setResultCode(Integer resultCode){
        this.resultCode = resultCode;
    }

    public String getResultMsg(){
        return resultMsg;
    }

    public void setResultMsg(String resultMsg){
        this.resultMsg = resultMsg;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }
}
