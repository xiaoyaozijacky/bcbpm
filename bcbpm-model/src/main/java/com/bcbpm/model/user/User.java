package com.bcbpm.model.user;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String userId;
    private String userName;
    private String trueName;
    private Integer userState;// 用户状态 0正常 1锁定
    private String userPic;
    private String email;
    private String mobile;
    private String tenantId;//租户标识
    private Integer isEnterprise;//是否是企业号申请人 0:不是 1:是
    private Date expirationDate;// 企业号到期日期
    private Integer effectiveDays;//购买有效天数
    private Integer remainingDays;// 还剩几天到期

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public Integer getUserState(){
        return userState;
    }

    public void setUserState(Integer userState){
        this.userState = userState;
    }

    public String getUserPic(){
        return userPic;
    }

    public void setUserPic(String userPic){
        this.userPic = userPic;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getTenantId(){
        return tenantId;
    }

    public void setTenantId(String tenantId){
        this.tenantId = tenantId;
    }

    public Integer getIsEnterprise(){
        return isEnterprise;
    }

    public void setIsEnterprise(Integer isEnterprise){
        this.isEnterprise = isEnterprise;
    }

    public Date getExpirationDate(){
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate){
        this.expirationDate = expirationDate;
    }

    public Integer getEffectiveDays(){
        return effectiveDays;
    }

    public void setEffectiveDays(Integer effectiveDays){
        this.effectiveDays = effectiveDays;
    }

    public Integer getRemainingDays(){
        return remainingDays;
    }

    public void setRemainingDays(Integer remainingDays){
        this.remainingDays = remainingDays;
    }

    public String getTrueName(){
        return trueName;
    }

    public void setTrueName(String trueName){
        this.trueName = trueName;
    }

}