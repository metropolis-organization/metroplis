package com.metropolis.sso.auth.sys;

import com.metropolis.sso.auth.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * @author Pop
 * @date 2020/2/27 22:28
 *
 * 简单的存储下用户的信息
 */
@Component
public class SysSession {

    private ThreadLocal<UserEntity> userThreadLocal = new ThreadLocal<>();

    public UserEntity getCurrentEntity(){ return userThreadLocal.get(); }
    public SysUser getCurrentUser(){return getCurrentEntity().getSysUser();}
    public void setCurrentEntity(UserEntity entity){userThreadLocal.set(entity);}
    public void clear(){ userThreadLocal.remove(); }
}
