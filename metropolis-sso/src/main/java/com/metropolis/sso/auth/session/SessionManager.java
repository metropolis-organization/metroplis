package com.metropolis.sso.auth.session;


import com.metropolis.common.web.dto.SysUserDto;

/**
 * 认证中心应具有管理session的能力
 * 各个系统登录后，由此对象管理全局session，全局session
 * 管理着用于在此管理下的session情况
 */
public interface SessionManager {

    /**
     * 记录某个用户的session进入管理器
     * @param sysUser 用户信息
     * @param group 模块组别
     */
    void addSession(SysUserDto sysUser, String group,String sessionId);

    /**
     * 此操作将会删除用户全局session，连同所有模块下的session
     * @param sysUser
     */
    void delSession(SysUserDto sysUser,String group);
}
