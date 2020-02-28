package com.metropolis.authorization.controller;

import com.metropolis.authorization.dal.entitys.SysUser;
import com.metropolis.common.web.dto.SysUserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author Pop
 * @date 2020/2/16 21:45
 */
public class ShiroController {

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected SysUserDto getCurrentUser() {
        return (SysUserDto) getSubject().getPrincipal();
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token){ getSubject().login(token); }

    protected UsernamePasswordToken getToken(SysUser user,boolean rememberMe){
        return new UsernamePasswordToken(user.getUsername(),user.getPassword(),rememberMe);
    }
    protected UsernamePasswordToken getToken(SysUser user){
        return new UsernamePasswordToken(user.getUsername(),user.getPassword(),false);
    }


}
