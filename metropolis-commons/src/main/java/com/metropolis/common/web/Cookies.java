package com.metropolis.common.web;


import com.metropolis.common.sso.SsoConstant;
import com.metropolis.common.string.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Pop
 * @date 2020/2/27 17:49
 *
 * cookie 工具
 */
public class Cookies {
    private static final String PATH = "/shiroCookies";
    public static void addCookie(String name, String value, HttpServletResponse response){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(PATH);
        response.addCookie(cookie);
    }

    public static Cookie getCookieByName(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){ return null;}
        for (Cookie cookie:cookies){
            if (StringUtils.equals(name,cookie.getName())){
                return cookie;
            }
        }
        return null;
    }

}
