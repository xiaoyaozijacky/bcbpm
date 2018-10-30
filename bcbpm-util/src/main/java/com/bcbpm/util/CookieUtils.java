package com.bcbpm.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtils{
    static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    //	/**
    //     * 设置cookie
    //     * @param name cookie名字
    //     * @param value cookie值
    //     */
    //    public static void addCookie(String name, String value) {
    //        try {
    //            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
    //            cookie.setPath("/");
    //            cookie.setMaxAge(COOKIE_AGE);
    //            ResponseThreadLocal.get().addCookie(cookie);
    //        } catch (Exception e) {
    //            log.error("设置cookie", e);
    //        }
    //    }
    // 
    /**
     * 根据名字获取cookie
     * @param name cookie名字
     * @return String
     */
    public static String getCookie(String name, HttpServletRequest request){
        try{
            Map<String, Cookie> cookieMap = readCookieMap(request);
            if(cookieMap.containsKey(name)){
                Cookie cookie = (Cookie) cookieMap.get(name);
                return URLDecoder.decode(cookie.getValue(), "UTF-8");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("根据名字获取cookie", e);
        }
        return "";
    }

    // 将cookie封装到Map里面
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for(Cookie cookie : cookies){
                if(cookie != null){
                    cookieMap.put(cookie.getName(), cookie);
                }
            }
        }
        return cookieMap;
    }
}
