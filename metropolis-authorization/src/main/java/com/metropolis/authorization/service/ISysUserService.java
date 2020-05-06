package com.metropolis.authorization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.metropolis.authorization.dal.entitys.SysUser;

/**
 * @author Pop
 * @date 2020/2/16 19:24
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名，获得用户
     * @param username
     * @return
     */
    SysUser findUserByName(String username);

    /**
     * 根据账号，获得用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 增加一个用户
     * @param user
     */
    void saveUser(SysUser user);
}
