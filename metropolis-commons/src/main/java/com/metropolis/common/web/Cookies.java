package com.metropolis.common.web;


import com.metropolis.common.sso.SsoConstant;
import com.metropolis.common.string.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author Pop
 * @date 2020/2/27 17:49
 *
 * cookie 工具
 */
public class Cookies {
    private static final String PATH = "/";
    public static void addCookie(String name, String value, HttpServletResponse response){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(PATH);
        response.addCookie(cookie);
    }

    public static Cookie getCookieByName(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (StringUtils.nonEquals(name,cookie.getName())){
                return cookie;
            }
        }
        return null;
    }

}
