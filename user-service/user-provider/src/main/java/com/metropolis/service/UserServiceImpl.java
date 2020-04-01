package com.metropolis.service;

import com.metropolis.user.IUserService;
import com.metropolis.user.entity.SysUser;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author Pop
 * @date 2020/3/17 17:14
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public SysUser getUserById(Long id) {
        return null;
    }

    @Override
    public SysUser getUserByName(String name) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("pop");

        return sysUser;
    }
}
