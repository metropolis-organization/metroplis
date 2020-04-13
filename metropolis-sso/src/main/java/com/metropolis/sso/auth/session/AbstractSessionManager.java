package com.metropolis.sso.auth.session;


import com.metropolis.common.web.dto.SysUserDto;

/**
 * @program: metroplis
 * @description:
 * @author: Pop
 * @create: 2020-04-13 14:42
 **/
public abstract class AbstractSessionManager implements SessionManager{

    abstract String getKey(SysUserDto sysUser);

}
