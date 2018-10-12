package com.bcbpm.util;

import java.util.Random;
import java.util.UUID;

/**
 * <p>Title:随机生成uuid</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月11日 下午5:09:47
 * @version :
 * @description:
 */
public class UuidUtil{
    public static String newGeneratId(){
        UUID uuid = UUID.randomUUID();
        String newid = uuid.toString().replaceAll("-", "");
        return newid;
    }

    /**
     * 随机生成x位字符
     * @param length
     * @return
     */
    public static String getRandomString(int length){ //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @Title: 随机生成4位验证码 
     * @author jacky
     * @date 2018年10月11日 下午5:10:23
     * @return
     */
    public static String getSmsCode(){ //length表示生成字符串的长度  
        Random d = new Random();
        String str = "";
        for(int i = 0; i < 4; i++){
            int num = d.nextInt(10);
            str += num + "";
        }
        return str;
    }

}
