package com.metropolis.user;

import com.metropolis.user.entity.SysUser;

/**
 * @author Pop
 * @date 2020/3/17 16:58
 */
public interface IUserService {

    SysUser getUserById(Long id);

    SysUser getUserByName(String name);


}
