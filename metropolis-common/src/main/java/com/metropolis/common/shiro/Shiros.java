package com.metropolis.common.shiro;

import com.metropolis.common.web.dto.SysUserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-13 17:30
 **/
public class Shiros {
    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUserDto getCurrentUser() {
        return (SysUserDto) getSubject().getPrincipal();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    public static void login(AuthenticationToken token){ getSubject().login(token); }
    public static void logout(){getSubject().logout();}

    public static UsernamePasswordToken getToken(SysUserDto user, boolean rememberMe){
        return new UsernamePasswordToken(user.getUsername(),user.getPassword(),rememberMe);
    }
    public static UsernamePasswordToken getToken(SysUserDto user){
        return new UsernamePasswordToken(user.getUsername(),user.getPassword(),false);
    }
}
